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
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdventureWorksData {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdventureWorksData.class);

	private List<Map<String, Object>> categories = new ArrayList<>();
	private List<Map<String, Object>> products = new ArrayList<>();
	private List<Map<String, Object>> descriptions = new ArrayList<>();
	private List<Map<String, Object>> images = new ArrayList<>();
	private List<Map<String, Object>> imagesLink = new ArrayList<>();
	private List<Map<String, Object>> variants = new ArrayList<>();
	private List<Map<String, Object>> orderHeader = new ArrayList<>();
	private List<Map<String, Object>> orderDetail = new ArrayList<>();
	private List<Map<String, Object>> descriptionsLink = new ArrayList<>();
	private Path directory = Paths.get(".");

	void clean(final String fileName, final String[] patternsToRemove, final Charset encoding) throws IOException {

		String fileNameOnly = FilenameUtils.getBaseName(fileName);
		String fileExtensionOnly = FilenameUtils.getExtension(fileName);

		String textFromFile = FileUtils.readFileToString(new File(directory.toString(), fileName), encoding);
		for (final String pattern : patternsToRemove) {
			textFromFile = textFromFile.replaceAll(pattern, "");
		}
		FileUtils.write(new File(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly), textFromFile, encoding);
	}

	public List<Map<String, Object>> getCategories() {
		return categories;
	}

	public List<Map<String, Object>> getDescriptions() {
		return descriptions;
	}

	public List<Map<String, Object>> getDescriptionsLink() {
		return descriptionsLink;
	}

	public List<Map<String, Object>> getImages() {
		return images;
	}

	public List<Map<String, Object>> getImagesLink() {
		return imagesLink;
	}

	public List<Map<String, Object>> getOrderDetail() {
		return orderDetail;
	}

	public List<Map<String, Object>> getOrderHeader() {
		return orderHeader;
	}

	public List<Map<String, Object>> getProducts() {
		return products;
	}

	public List<Map<String, Object>> getVariants() {
		return variants;
	}

	void initialize() throws IOException {

		clean("ProductModel.csv",
				new String[] { "<root.+?>[\\s\\S]+?</root>", "<p1:ProductDescription.+?>[\\s\\S]+?</p1:ProductDescription>", "<\\?.+?\\?>" },
				StandardCharsets.UTF_16LE);

		clean("ProductDescription.csv", new String[] { "\"" }, StandardCharsets.UTF_16LE);

		setCategories(read("ProductSubcategory.csv", CSVFormat.TDF.withHeader("id", "parent", "name", "guid", "date"), StandardCharsets.US_ASCII));
		setProducts(
				read("ProductModel.csv", CSVFormat.TDF.withDelimiter('|').withHeader("id", "name", "description", "instructions", "guid", "modified"),
						StandardCharsets.UTF_16LE));
		setDescriptions(read("ProductDescription.csv", CSVFormat.TDF.withRecordSeparator("\n").withHeader("id", "description", "guid", "modified"),
				StandardCharsets.UTF_16LE));
		setDescriptionsLink(read("ProductModelProductDescriptionCulture.csv", CSVFormat.TDF.withHeader("model", "description", "culture", "modified"),
				StandardCharsets.US_ASCII));
		setImages(read("ProductPhoto.csv",
				CSVFormat.TDF.withDelimiter('|').withHeader("id", "thumbnail", "thumbnail_filename", "large", "large_filename", "date"),
				StandardCharsets.UTF_16LE));
		setImagesLink(
				read("ProductProductPhoto.csv", CSVFormat.TDF.withHeader("product", "image", "primary", "modified"), StandardCharsets.US_ASCII));
		setVariants(read("Product.csv",
				CSVFormat.TDF.withHeader("id", "name", "sku", "make", "finished", "color", "safetyStockLevel", "reorderPoint", "cost", "price",
						"size", "sizeUnit", "weightUnit", "weight", "daysToManufacture", "productLine", "class", "style", "subcategory", "model",
						"sellStartDate", "sellEndDate", "discontinuedDate", "guid", "modified"),
				StandardCharsets.US_ASCII));
		setOrderHeader(read("SalesOrderHeader.csv",
				CSVFormat.TDF.withHeader("orderId", "revisionNumber", "orderDate", "dueDate", "shipDate", "status", "isOnline", "onlineNumber",
						"poNumber", "accountNumber", "customer", "salesPerson", "territory", "billTo", "shipTo", "shipMethod", "cc", "ccCode",
						"currency", "subTotal", "tax", "freight", "total", "comment", "guid", "date"),
				StandardCharsets.US_ASCII));
		setOrderDetail(read("SalesOrderDetail.csv", CSVFormat.TDF.withHeader("orderId", "recordId", "tracking", "quantity", "productId", "offerId",
				"price", "discount", "total", "guid", "date"), StandardCharsets.US_ASCII));

		variants.forEach(v -> {
			Stream<Map<String, Object>> filtered1 = imagesLink.stream().filter(l, v -> {l.get("product").equals(v.get("id"))});
			
			//v.put("image", imagesLink.stream().filter(l -> l.get("product").equals(v.get("id"))).filter(l -> {}));
			v.put("category", categories.stream().filter(c -> c.get("id").equals(v.get("subcategory"))));
		});
	}

	public List<Map<String, Object>> read(final String fileName, final CSVFormat format, final Charset encoding) throws IOException {
		List<Map<String, Object>> result = new ArrayList<>();
		List<CSVRecord> records = _read(fileName, format, encoding);
		records.forEach(r -> {
			Map<String, Object> m = new HashMap<>();
			r.toMap().forEach((k, v) -> {
				m.put(k, v);
			});
			result.add(m);
		});

		return result;
	}

	public List<CSVRecord> _read(final String fileName, final CSVFormat format, final Charset encoding) throws IOException {
		String fileNameOnly = FilenameUtils.getBaseName(fileName);
		String fileExtensionOnly = FilenameUtils.getExtension(fileName);

		if (Files.exists(Paths.get(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly))) {
			LOGGER.info("reading: {}", Paths.get(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly));
			return CSVParser.parse(new File(directory.toString(), fileNameOnly + ".clean." + fileExtensionOnly), encoding, format).getRecords();
		}
		return CSVParser.parse(new File(directory.toString(), fileName), encoding, format).getRecords();
	}

	public void setCategories(final List<Map<String, Object>> categories) {
		this.categories = categories;
	}

	public void setDescriptions(final List<Map<String, Object>> descriptions) {
		this.descriptions = descriptions;
	}

	private void setDescriptionsLink(final List<Map<String, Object>> descriptionsLink) {
		this.descriptionsLink = descriptionsLink;
	}

	private void setDirectory(final Path directory) {
		this.directory = directory;
	}

	public void setImages(final List<Map<String, Object>> images) {
		this.images = images;
	}

	public void setImagesLink(final List<Map<String, Object>> imagesLink) {
		this.imagesLink = imagesLink;
	}

	public void setOrderDetail(final List<Map<String, Object>> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public void setOrderHeader(final List<Map<String, Object>> orderHeader) {
		this.orderHeader = orderHeader;
	}

	public void setProducts(final List<Map<String, Object>> products) {
		this.products = products;
	}

	public void setVariants(final List<Map<String, Object>> variants) {
		this.variants = variants;
	}

	public AdventureWorksData(final Path directory) {
		setDirectory(directory);
	}

}
