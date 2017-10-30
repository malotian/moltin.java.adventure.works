package com.moltin.adventure.works;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AdventureWorksData {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdventureWorksData.class);

	private JsonArray categories = new JsonArray();
	private JsonArray descriptions = new JsonArray();
	private JsonArray descriptionsLink = new JsonArray();
	private Path directory = Paths.get(".");
	private JsonArray images = new JsonArray();
	private JsonArray imagesLink = new JsonArray();
	private JsonArray orderDetail = new JsonArray();
	private JsonArray orderHeader = new JsonArray();
	private JsonArray products = new JsonArray();

	private JsonArray variants = new JsonArray();

	public AdventureWorksData(final Path directory) {
		setDirectory(directory);
	}

	public List<CSVRecord> _read(final String fileName, final CSVFormat format, final Charset encoding) throws IOException {
		final String fileNameOnly = FilenameUtils.getBaseName(fileName);
		final String fileExtensionOnly = FilenameUtils.getExtension(fileName);

		if (Files.exists(Paths.get(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly))) {
			LOGGER.info("reading: {}", Paths.get(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly));
			return CSVParser.parse(new File(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly), encoding, format).getRecords();
		}
		return CSVParser.parse(new File(directory.toString(), fileName), encoding, format).getRecords();
	}

	void checkSanity() {

		final JsonObject groupedBySku = new JsonObject();

		getVariants().forEach(_variant -> {
			final JsonObject variant = _variant.getAsJsonObject();

			final String sku = variant.get("sku").getAsString().substring(0, 7);

			if (!groupedBySku.has(sku)) {
				groupedBySku.add(sku, new JsonArray());
			}

			final int size1 = groupedBySku.get(sku).getAsJsonArray().size();

			if (!groupedBySku.get(sku).getAsJsonArray().contains(variant.get("model"))) {
				groupedBySku.get(sku).getAsJsonArray().add(variant.get("model"));
			}
			final int size2 = groupedBySku.get(sku).getAsJsonArray().size();

			if (size2 > size1 && size2 > 1) {
				LOGGER.error("variants with same sku: {}, but differnet models: {}", sku, groupedBySku.get(sku));
				throw new RuntimeException("invalid data - products.csv");
			}

		});
	}

	void clean(final String fileName, final String[] patternsToRemove, final Charset encoding) throws IOException {

		final String fileNameOnly = FilenameUtils.getBaseName(fileName);
		final String fileExtensionOnly = FilenameUtils.getExtension(fileName);

		String textFromFile = FileUtils.readFileToString(new File(directory.toString(), fileName), encoding);
		for (final String pattern : patternsToRemove) {
			textFromFile = textFromFile.replaceAll(pattern, "");
		}
		FileUtils.write(new File(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly), textFromFile, encoding);
	}

	public void dump() throws JsonProcessingException, IOException {

		final File dumpDirectory = new File(directory.toString(), "dump");

		FileUtils.deleteDirectory(dumpDirectory);
		FileUtils.forceMkdir(dumpDirectory);

		final Gson gson = new GsonBuilder().setPrettyPrinting().create();

		FileUtils.write(new File(dumpDirectory, "categories.json"), gson.toJson(categories), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "products.json"), gson.toJson(products), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "descriptions.json"), gson.toJson(descriptions), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "images.json"), gson.toJson(images), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "imagesLink.json"), gson.toJson(imagesLink), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "variants.json"), gson.toJson(variants), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "orderHeader.json"), gson.toJson(orderHeader), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "orderDetail.json"), gson.toJson(orderDetail), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "descriptionsLink.json"), gson.toJson(descriptionsLink), StandardCharsets.US_ASCII);
	}

	public JsonArray getCategories() {
		return categories;
	}

	public JsonArray getDescriptions() {
		return descriptions;
	}

	public JsonArray getDescriptionsLink() {
		return descriptionsLink;
	}

	public Path getDirectory() {
		return directory;
	}

	public JsonArray getImages() {
		return images;
	}

	public JsonArray getImagesLink() {
		return imagesLink;
	}

	public JsonArray getInventory() {
		return getProducts();
	}

	public JsonArray getOrderDetail() {
		return orderDetail;
	}

	public JsonArray getOrderHeader() {
		return orderHeader;
	}

	public JsonArray getProducts() {
		return products;
	}

	public JsonArray getTransactions() {
		return getOrderHeader();
	}

	public JsonArray getVariants() {
		return variants;
	}

	public void initialize() throws IOException {

		clean("ProductModel.csv", new String[] { "<root.+?>[\\s\\S]+?</root>", "<p1:ProductDescription.+?>[\\s\\S]+?</p1:ProductDescription>", "<\\?.+?\\?>" },
				StandardCharsets.UTF_16);

		clean("ProductDescription.csv", new String[] { "\"" }, StandardCharsets.UTF_16);

		setCategories(read("ProductSubcategory.csv", CSVFormat.TDF.withHeader("id", "parent", "name", "guid", "date"), StandardCharsets.US_ASCII));
		setProducts(read("ProductModel.csv", CSVFormat.TDF.withDelimiter('|').withHeader("id", "name", "description", "instructions", "guid", "modified"), StandardCharsets.UTF_16,
				true));
		setDescriptions(read("ProductDescription.csv", CSVFormat.TDF.withRecordSeparator("\n").withHeader("id", "description", "guid", "modified"), StandardCharsets.UTF_16));
		setDescriptionsLink(read("ProductModelProductDescriptionCulture.csv", CSVFormat.TDF.withHeader("model", "description", "culture", "modified"), StandardCharsets.US_ASCII));
		setImages(read("ProductPhoto.csv", CSVFormat.TDF.withDelimiter('|').withHeader("id", "thumbnail", "thumbnail_filename", "large", "large_filename", "date"),
				StandardCharsets.UTF_16, true));
		setImagesLink(read("ProductProductPhoto.csv", CSVFormat.TDF.withHeader("product", "image", "primary", "modified"), StandardCharsets.US_ASCII));
		setVariants(read("Product.csv",
				CSVFormat.TDF.withHeader("id", "name", "sku", "make", "finished", "color", "safetyStockLevel", "reorderPoint", "cost", "price", "size", "sizeUnit", "weightUnit",
						"weight", "daysToManufacture", "productLine", "class", "style", "subcategory", "model", "sellStartDate", "sellEndDate", "discontinuedDate", "guid",
						"modified"),
				StandardCharsets.US_ASCII));
		setOrderHeader(read("SalesOrderHeader.csv",
				CSVFormat.TDF.withHeader("orderId", "revisionNumber", "orderDate", "dueDate", "shipDate", "status", "isOnline", "onlineNumber", "poNumber", "accountNumber",
						"customer", "salesPerson", "territory", "billTo", "shipTo", "shipMethod", "cc", "ccCode", "currency", "subTotal", "tax", "freight", "total", "comment",
						"guid", "date"),
				StandardCharsets.US_ASCII));
		setOrderDetail(read("SalesOrderDetail.csv",
				CSVFormat.TDF.withHeader("orderId", "recordId", "tracking", "quantity", "productId", "offerId", "price", "discount", "total", "guid", "date"),
				StandardCharsets.US_ASCII));

		final File imagesDirectory = new File(directory.toString(), "images");
		FileUtils.deleteDirectory(imagesDirectory);
		FileUtils.forceMkdir(imagesDirectory);

		images.forEach(_i -> {
			final JsonObject i = _i.getAsJsonObject();
			final String thumbnail = i.get("thumbnail").getAsString();
			final String thumbnailFilename = i.get("thumbnail_filename").getAsString();
			final String large = i.get("large").getAsString();
			final String largeFilename = i.get("large_filename").getAsString();
			LOGGER.info("Processing {} and {}", thumbnailFilename, largeFilename);
			try {

				ImageIO.write(ImageIO.read(new ByteArrayInputStream(DatatypeConverter.parseHexBinary(thumbnail))), "png",
						new FileOutputStream(new File(imagesDirectory, thumbnailFilename.replaceAll("gif", "png"))));

				ImageIO.write(ImageIO.read(new ByteArrayInputStream(DatatypeConverter.parseHexBinary(large))), "png",
						new FileOutputStream(new File(imagesDirectory, largeFilename.replaceAll("gif", "png"))));

			} catch (final IOException e) {
				LOGGER.error("error, processing {} and {}, exception: {}", thumbnailFilename, largeFilename, ExceptionUtils.getStackTrace(e));
			}
		});

		variants.forEach(_v -> {
			final JsonObject v = _v.getAsJsonObject();
			imagesLink.forEach(_l -> {
				final JsonObject l = _l.getAsJsonObject();
				if (l.get("product").equals(v.get("id"))) {
					images.forEach(_i -> {
						final JsonObject i = _i.getAsJsonObject();
						if (i.get("id").equals(l.get("image"))) {
							v.add("image", i);
						}
					});
				}
			});

			categories.forEach(_c -> {
				final JsonObject c = _c.getAsJsonObject();
				if (c.get("id").equals(v.get("subcategory"))) {
					v.add("category", c);
					// LOGGER.debug("variant: {}, category: {}", v, c);

				}
			});
		});

		products.forEach(_p -> {
			final JsonObject p = _p.getAsJsonObject();
			p.addProperty("description", "Description not available");

			descriptionsLink.forEach(_l -> {
				final JsonObject l = _l.getAsJsonObject();
				if (l.get("model").equals(p.get("id")) && "en".equals(l.get("culture"))) {
					descriptions.forEach(_d -> {
						final JsonObject d = _d.getAsJsonObject();
						if (d.get("id").equals(l.get("description"))) {
							p.add("description", d.get("description"));
						}
					});
				}
			});

			final JsonArray colors = new JsonArray();
			final JsonArray sizes = new JsonArray();
			final JsonArray varities = new JsonArray();
			variants.forEach(_v -> {
				final JsonObject v = _v.getAsJsonObject();
				if (v.get("model").equals(p.get("id")) && v.has("category")) {

					varities.add(v);
					if (v.has("color") && StringUtils.isNotBlank(v.get("color").getAsString()) && !colors.contains(v.get("color"))) {
						if (!colors.contains(v.get("color"))) {
							colors.add(v.get("color"));
						}
					}

					if (v.has("size") && StringUtils.isNotBlank(v.get("size").getAsString()) && !colors.contains(v.get("size"))) {
						if (!sizes.contains(v.get("size"))) {
							sizes.add(v.get("size"));
						}
					}
				}
			});

			final JsonObject mod1 = new JsonObject();
			mod1.addProperty("title", "color");
			mod1.add("values", colors);

			final JsonObject mod2 = new JsonObject();
			mod2.addProperty("title", "size");
			mod2.add("values", sizes);

			final JsonArray modifiers = new JsonArray();
			modifiers.add(mod1);
			modifiers.add(mod2);

			p.add("variants", varities);
			p.add("modifiers", modifiers);

		});

		orderDetail.forEach(_line -> {
			final JsonObject line = _line.getAsJsonObject();
			variants.forEach(_v1 -> {
				final JsonObject v1 = _v1.getAsJsonObject();
				if (v1.get("id").equals(line.get("productId"))) {
					line.add("sku", v1.get("sku"));
				}
			});
		});

		final JsonObject groupedDetails = new JsonObject();
		orderDetail.forEach(_od -> {
			final JsonObject od = _od.getAsJsonObject();
			final String orderId = od.get("orderId").getAsString();

			if (!groupedDetails.has(orderId)) {
				groupedDetails.add(orderId, new JsonArray());
			}
			groupedDetails.get(orderId).getAsJsonArray().add(od);
		});

		orderHeader.forEach(_oh -> {
			final JsonObject oh = _oh.getAsJsonObject();
			oh.add("details", groupedDetails.get("orderId"));
		});

		checkSanity();
	}

	public JsonArray read(final String fileName, final CSVFormat format, final Charset encoding) throws IOException {
		return read(fileName, format, encoding, false);
	}

	public JsonArray read(final String fileName, final CSVFormat format, final Charset encoding, final boolean strip) throws IOException {
		final JsonArray ja = new JsonArray();
		final List<CSVRecord> records = _read(fileName, format, encoding);
		records.forEach(r -> {
			final JsonObject jo = new JsonObject();
			r.toMap().forEach((k, v) -> {
				if (strip) {
					v = StringUtils.substring(v, 0, -1);
				}

				jo.addProperty(k, v);
			});
			ja.add(jo);
		});
		return ja;
	}

	public void setCategories(final JsonArray categories) {
		this.categories = categories;
	}

	public void setDescriptions(final JsonArray descriptions) {
		this.descriptions = descriptions;
	}

	private void setDescriptionsLink(final JsonArray descriptionsLink) {
		this.descriptionsLink = descriptionsLink;
	}

	private void setDirectory(final Path directory) {
		this.directory = directory;
	}

	public void setImages(final JsonArray images) {
		this.images = images;
	}

	public void setImagesLink(final JsonArray imagesLink) {
		this.imagesLink = imagesLink;
	}

	public void setOrderDetail(final JsonArray orderDetail) {
		this.orderDetail = orderDetail;
	}

	public void setOrderHeader(final JsonArray orderHeader) {
		this.orderHeader = orderHeader;
	}

	public void setProducts(final JsonArray products) {
		this.products = products;
	}

	public void setVariants(final JsonArray variants) {
		this.variants = variants;
	}

}
