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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdventureWorksData {

	@SuppressWarnings("serial")
	class Table extends ArrayList<Tuple> {

		public Table addX(Tuple tuple) {
			super.add(tuple);
			return this;
		}
	}

	@SuppressWarnings("serial")
	class Tuple extends HashMap<String, Object> {
		public Tuple putX(String key, Object value) {
			super.put(key, value);
			return this;
		}
	}

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

	public Table getOrderDetail() {
		return orderDetail;
	}

	public Table getOrderHeader() {
		return orderHeader;
	}

	public Table getProducts() {
		return products;
	}

	public Table getVariants() {
		return variants;
	}

	void initialize() throws IOException {

		clean("ProductModel.csv", new String[] { "<root.+?>[\\s\\S]+?</root>", "<p1:ProductDescription.+?>[\\s\\S]+?</p1:ProductDescription>", "<\\?.+?\\?>" },
				StandardCharsets.UTF_16LE);

		clean("ProductDescription.csv", new String[] { "\"" }, StandardCharsets.UTF_16LE);

		setCategories(read("ProductSubcategory.csv", CSVFormat.TDF.withHeader("id", "parent", "name", "guid", "date"), StandardCharsets.US_ASCII));
		setProducts(
				read("ProductModel.csv", CSVFormat.TDF.withDelimiter('|').withHeader("id", "name", "description", "instructions", "guid", "modified"), StandardCharsets.UTF_16LE));
		setDescriptions(read("ProductDescription.csv", CSVFormat.TDF.withRecordSeparator("\n").withHeader("id", "description", "guid", "modified"), StandardCharsets.UTF_16LE));
		setDescriptionsLink(read("ProductModelProductDescriptionCulture.csv", CSVFormat.TDF.withHeader("model", "description", "culture", "modified"), StandardCharsets.US_ASCII));
		setImages(read("ProductPhoto.csv", CSVFormat.TDF.withDelimiter('|').withHeader("id", "thumbnail", "thumbnail_filename", "large", "large_filename", "date"),
				StandardCharsets.UTF_16LE));
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
				if (l.get("product").equals(v.get("id"))) {
					images.forEach(i -> {
						if (i.get("id").equals(l.get("image"))) {
							v.put("image", i);
							LOGGER.debug("variant: {}, image: {}", v, i);
						}
					});
				}
			});

			categories.forEach(c -> {
				if (c.get("id").equals(v.get("subcategory"))) {
					v.put("category", c);
					LOGGER.debug("variant: {}, category: {}", v, c);

				}
			});
		});

		products.forEach(p -> {
			p.put("description", "Description not available");

			descriptionsLink.forEach(l -> {
				if (l.get("model").equals(p.get("id")) && "en".equals(l.get("culture"))) {
					descriptions.forEach(d -> {
						if (d.get("id").equals(l.get("description"))) {
							p.put("description", d.get("description"));
						}
					});
				}
			});

			final List<String> colors = new ArrayList<>();
			final List<String> sizes = new ArrayList<>();
			final Table varities = new Table();
			variants.forEach(v -> {
				if (v.get("model").equals(p.get("id")) && v.containsKey("category")) {
					varities.add(v);
					colors.add(p.get("color").toString());
					sizes.add(p.get("size").toString());
				}
			});

			p.put("variants", varities);
			p.put("modifiers", new Table().addX(new Tuple().putX("title", "color").putX("values", colors)).addX(new Tuple().putX("title", "size").putX("values", sizes)));

		});

		orderDetail.forEach(line -> {
			variants.forEach(v1 -> {
				if (v1.get("id").equals(line.get("productId"))) {
					line.put("sku", v1.get("sku"));
				}
			});
		});

		final Map<String, Table> groupedDetails = new HashMap<>();
		for (final Tuple od : orderDetail) {

			if (!groupedDetails.containsKey(od.get("orderId"))) {
				groupedDetails.put(od.get("orderId").toString(), new Table());
			}

			final Table group = groupedDetails.get(od.get("orderId").toString());
			group.add(od);
		}

		orderHeader.forEach(oh -> {
			oh.put("details", groupedDetails.get("orderId"));
		});

	}

	public Table read(final String fileName, final CSVFormat format, final Charset encoding) throws IOException {
		final Table result = new Table();
		final List<CSVRecord> records = _read(fileName, format, encoding);
		records.forEach(r -> {
			final Tuple m = new Tuple();
			r.toMap().forEach((k, v) -> {
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
