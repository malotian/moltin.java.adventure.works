package com.moltin.adventure.works;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

public class RecommendationHelper {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationHelper.class);

	@SuppressWarnings("resource")
	public void prepare(final AdventureWorksData awd) throws IOException {

		CSVPrinter recommendationsCatalogFile = new CSVPrinter(new FileWriter(new File(awd.getDirectory().toFile(), "recommendations-catalog.csv")),
				CSVFormat.DEFAULT);

		awd.getProducts().forEach(_product -> {
			JsonObject product = _product.getAsJsonObject();

			product.getAsJsonArray("variants").forEach(_variant -> {
				JsonObject variant = _variant.getAsJsonObject();
				try {
					recommendationsCatalogFile.printRecord(variant.get("sku").getAsString().replaceAll(",", ""),
							variant.get("name").getAsString().replaceAll(",", "-"),
							variant.getAsJsonObject("category").get("name").getAsString().replaceAll(",", ""),
							product.get("description").getAsString().replaceAll(",", ""));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		});
		
		recommendationsCatalogFile.close();

		CSVPrinter recommendationsUsageFile = new CSVPrinter(new FileWriter(new File(awd.getDirectory().toFile(), "recommendations-usage.csv")),
				CSVFormat.DEFAULT);

		awd.getTransactions().forEach(_transaction -> {
			JsonObject transaction = _transaction.getAsJsonObject();
			if (transaction.has("details")) {
				// LOGGER.debug("transaction: {}", transaction);
				transaction.getAsJsonArray("details").forEach(_orderDetail -> {
					JsonObject orderDetail = _orderDetail.getAsJsonObject();
					try {
						recommendationsUsageFile.printRecord(transaction.get("customer").getAsString(),
								orderDetail.get("sku").getAsString(),
								transaction.get("orderDate").getAsString(),
								"Purchase");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		});
		
		recommendationsUsageFile.close();

	}
}
