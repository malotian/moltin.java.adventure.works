package com.moltin.adventure.works;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class AdventureWorksData {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdventureWorksData.class);

	private Table categories = new Table();
	private Table products = new Table();
	private Table descriptions = new Table();
	private Table images = new Table();
	private Table imagesLink = new Table();
	private Table variants = new Table();
	private Table orderHeader = new Table();
	private Table orderDetail = new Table();
	private Table descriptionsLink = new Table();
	private Path directory = Paths.get(".");

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

	void clean(final String fileName, final String[] patternsToRemove, final Charset encoding) throws IOException {

		final String fileNameOnly = FilenameUtils.getBaseName(fileName);
		final String fileExtensionOnly = FilenameUtils.getExtension(fileName);

		String textFromFile = FileUtils.readFileToString(new File(directory.toString(), fileName), encoding);
		for (final String pattern : patternsToRemove) {
			textFromFile = textFromFile.replaceAll(pattern, "");
		}
		FileUtils.write(new File(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly), textFromFile, encoding);
	}

	void dump() throws JsonProcessingException, IOException {

		final File dumpDirectory = new File(directory.toString(), "processed");

		FileUtils.deleteDirectory(dumpDirectory);
		FileUtils.forceMkdir(dumpDirectory);

		final ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
		FileUtils.write(new File(dumpDirectory, "categories.json"), writer.writeValueAsString(categories), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "products.json"), writer.writeValueAsString(products), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "descriptions.json"), writer.writeValueAsString(descriptions), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "images.json"), writer.writeValueAsString(images), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "imagesLink.json"), writer.writeValueAsString(imagesLink), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "variants.json"), writer.writeValueAsString(variants), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "orderHeader.json"), writer.writeValueAsString(orderHeader), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "orderDetail.json"), writer.writeValueAsString(orderDetail), StandardCharsets.US_ASCII);
		FileUtils.write(new File(dumpDirectory, "descriptionsLink.json"), writer.writeValueAsString(descriptionsLink), StandardCharsets.US_ASCII);
	}

	public Table getCategories() {
		return categories;
	}

	public Table getDescriptions() {
		return descriptions;
	}

	public Table getDescriptionsLink() {
		return descriptionsLink;
	}

	public Table getImages() {
		return images;
	}

	public Table getImagesLink() {
		return imagesLink;
	}

	public Table getInventory() {
		return getProducts();
	}

	public Table getOrderDetail() {
		return orderDetail;
	}

	public Table getOrderHeader() {
		return orderHeader;
	}

	public Table getProducts() {
		return products;
	}

	public Table getTransactions() {
		return getOrderHeader();
	}

	public Table getVariants() {
		return variants;
	}

	void initialize() throws IOException {

		clean("ProductModel.csv", new String[] { "<root.+?>[\\s\\S]+?</root>", "<p1:ProductDescription.+?>[\\s\\S]+?</p1:ProductDescription>", "<\\?.+?\\?>" },
				StandardCharsets.UTF_16LE);

		clean("ProductDescription.csv", new String[] { "\"" }, StandardCharsets.UTF_16LE);

		setCategories(read("ProductSubcategory.csv", CSVFormat.TDF.withHeader("id", "parent", "name", "guid", "date"), StandardCharsets.US_ASCII));
		setProducts(read("ProductModel.csv", CSVFormat.TDF.withDelimiter('|').withHeader("id", "name", "description", "instructions", "guid", "modified"),
				StandardCharsets.UTF_16LE, true));
		setDescriptions(read("ProductDescription.csv", CSVFormat.TDF.withRecordSeparator("\n").withHeader("id", "description", "guid", "modified"), StandardCharsets.UTF_16LE));
		setDescriptionsLink(read("ProductModelProductDescriptionCulture.csv", CSVFormat.TDF.withHeader("model", "description", "culture", "modified"), StandardCharsets.US_ASCII));
		setImages(read("ProductPhoto.csv", CSVFormat.TDF.withDelimiter('|').withHeader("id", "thumbnail", "thumbnail_filename", "large", "large_filename", "date"),
				StandardCharsets.UTF_16LE, true));
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

		variants.forEach(v -> {
			imagesLink.forEach(l -> {
				if (l.getString("product").equals(v.getString("id"))) {
					images.forEach(i -> {
						// LOGGER.debug("i.get(\"id\"): {}, l.get(\"image\"): {}", i.get("id"),
						// l.get("image"));
						if (i.getString("id").equals(l.getString("image"))) {
							v.put("image", i);
							// LOGGER.debug("variant: {}, image: {}", v, i);
						}
					});
				}
			});

			categories.forEach(c -> {
				if (c.getString("id").equals(v.getString("subcategory"))) {
					v.put("category", c);
					// LOGGER.debug("variant: {}, category: {}", v, c);

				}
			});
		});

		products.forEach(p -> {
			p.put("description", "Description not available");

			descriptionsLink.forEach(l -> {
				if (l.getString("model").equals(p.getString("id")) && "en".equals(l.getString("culture"))) {
					descriptions.forEach(d -> {
						if (d.getString("id").equals(l.getString("description"))) {
							p.put("description", d.getString("description"));
						}
					});
				}
			});

			final List<String> colors = new ArrayList<>();
			final List<String> sizes = new ArrayList<>();
			final Table varities = new Table();
			variants.forEach(v -> {
				if (v.getString("model").equals(p.getString("id")) && v.containsKey("category")) {
					varities.add(v);
					if (v.containsKey("color")) {
						colors.add(v.getString("color"));
					}
					if (v.containsKey("size")) {
						sizes.add(v.getString("size"));
					}
				}
			});

			p.put("variants", varities);
			p.put("modifiers", new Table().addX(new Tuple().putX("title", "color").putX("values", colors)).addX(new Tuple().putX("title", "size").putX("values", sizes)));

		});

		orderDetail.forEach(line -> {
			variants.forEach(v1 -> {
				if (v1.getString("id").equals(line.getString("productId"))) {
					line.put("sku", v1.getString("sku"));
				}
			});
		});

		final Map<String, Table> groupedDetails = new HashMap<>();
		for (final Tuple od : orderDetail) {

			if (!groupedDetails.containsKey(od.getString("orderId"))) {
				groupedDetails.put(od.getString("orderId"), new Table());
			}

			final Table group = groupedDetails.get(od.getString("orderId"));
			group.add(od);
		}

		orderHeader.forEach(oh -> {
			oh.put("details", groupedDetails.get("orderId"));
		});

	}

	public Table read(final String fileName, final CSVFormat format, final Charset encoding) throws IOException {
		return read(fileName, format, encoding, false);
	}

	public Table read(final String fileName, final CSVFormat format, final Charset encoding, boolean strip) throws IOException {
		final Table result = new Table();
		final List<CSVRecord> records = _read(fileName, format, encoding);
		records.forEach(r -> {
			final Tuple m = new Tuple();
			r.toMap().forEach((k, v) -> {
				if (strip) {
					v = StringUtils.substring(v, 0, -1);
				}
				m.put(k, v);
			});
			result.add(m);
		});

		return result;
	}

	public void setCategories(final Table categories) {
		this.categories = categories;
	}

	public void setDescriptions(final Table descriptions) {
		this.descriptions = descriptions;
	}

	private void setDescriptionsLink(final Table descriptionsLink) {
		this.descriptionsLink = descriptionsLink;
	}

	private void setDirectory(final Path directory) {
		this.directory = directory;
	}

	public void setImages(final Table images) {
		this.images = images;
	}

	public void setImagesLink(final Table imagesLink) {
		this.imagesLink = imagesLink;
	}

	public void setOrderDetail(final Table orderDetail) {
		this.orderDetail = orderDetail;
	}

	public void setOrderHeader(final Table orderHeader) {
		this.orderHeader = orderHeader;
	}

	public void setProducts(final Table products) {
		this.products = products;
	}

	public void setVariants(final Table variants) {
		this.variants = variants;
	}

}
