package de.broo.test.ebay;

/*

 Copyright (c) 2010 eBay, Inc.

 This program is licensed under the terms of the eBay Common Development and 
 Distribution License (CDDL) Version 1.0 (the "License") and any subsequent 
 version thereof released by eBay.  The then-current version of the License 
 can be found at https://www.codebase.ebay.com/Licenses.html and in the 
 eBaySDKLicense file that is under the eBay SDK install directory.
 */

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.helper.ConsoleUtil;
import com.ebay.sdk.call.GetCategoriesCall;
import com.ebay.sdk.call.GetItemCall;
import com.ebay.sdk.call.GeteBayOfficialTimeCall;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.SiteCodeType;

import de.broo.test.eclipselink.TODO;

/**
 * A Hello World-like sample, showing how to call eBay API using eBay SDK.
 * 
 * @author boyang
 * 
 */
public class ebay_Cat_import {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// System.out.print("\n");
			// System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
			// System.out.print("+ Welcome to eBay SDK for Java Sample +\n");
			// System.out.print("+  - HelloWorld                   +\n");
			// System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
			// System.out.print("\n");

			// [Step 1] Initialize eBay ApiContext object
			// System.out.println("===== [1] Account Information ====");
			ApiContext apiContext = getApiContext();

			// [Step 2] Create call object and execute the call
			System.out.println("Begin to call eBay API, please wait ... ");

			// DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[] {
			// DetailLevelCodeType.RETURN_ALL, };
			DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[] { DetailLevelCodeType.RETURN_ALL };

			String categoryID = "77";
			String categoryParentIDs[] = { categoryID };

			GetCategoriesCall getcategories = new GetCategoriesCall(apiContext);
			// getcategories.setParentCategories(categoryParentIDs);
			getcategories.setCategorySiteID(SiteCodeType.GERMANY);

			getcategories.setDetailLevel(detailLevels);
			// getcategories.setLevelLimit(1);
			getcategories.setViewAllNodes(true);

			// submit the GetCategories request
			CategoryType[] categoryArray = getcategories.getCategories();
		
			
			// stop to process further if the CategoryType object is null for
			// the given categoryID
		

			if (categoryArray == null) {
				System.out.println("The category " + categoryParentIDs[0]
						+ " is not available on the site ["
						+ apiContext.getSite().toString() + "]");
				return;
			}

			for (int i = 1; i < categoryArray.length; i++) {

			
			

				System.out.println("[" + categoryArray[i].getCategoryID() + "]"
						+ categoryArray[i].getCategoryName() + " ");

			}
			System.out.println("Count: " + categoryArray.length);

			System.out.println("");

		} catch (Exception e) {
			System.out.println("Fail to get eBay official time.");
			e.printStackTrace();
		}

	}

	/**
	 * Populate eBay SDK ApiContext object with data input from user
	 * 
	 * @return ApiContext object
	 * 
	 */
	private static ApiContext getApiContext() throws IOException {

		String input;
		ApiContext apiContext = new ApiContext();

		// set Api Token to access eBay Api Server
		ApiCredential cred = apiContext.getApiCredential();
		// input = ConsoleUtil
		// .readString("Enter your eBay Authentication Token: ");
		String token = "AgAAAA**AQAAAA**aAAAAA**S6C6Tw**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCJiGog6dj6x9nY+seQ**19ABAA**AAMAAA**Y3gISR5NIGJxNQWYG2QMGl6cryuJbnMKCgdwBriw9fN+bHYK2N9oXRlJTuJrSrZOdV51XqUR9cz9Z+tBVVq4YbN2lfs2IvAwJ+GaMEDMJrG/gCpM3/bqMBzCHReTK6enRxZ9s4JCecBK2FcdvYW+68lEhn9L33XEKgMQdQdtscWfaxDEbldPHznkv9JqHQXaiHC/0j94RtKNkCmUuUxKsbxPytNsYf0fA1QTJb01q1VfQPjrBhpkz2qsKcKo/74JKA7/TCtoQWAsCFrLKhosz9ahgGdr0tZL6n+siCRd6suIIz3ACTPJM7pDc5m4UCrG3FE3iSampRQZLAHOVcUFIIbXYoxhWvkDgQaVgvNP8qtK9H5WR3UX+XtHmowK1C9Qbd1m4CtNtoTYuT8ODeu8Hy6GznqmP0s6dGuSPB+CkdMiPOPbcnZ/vpqdP8K/sMrtFJXgnaVFrVBHhRMnMJ6uhzrXtJDqOv5FqlsfFfoANvHVGkGRocyYofRgmfbiZLVU5YRaBwna2qc0EV24IthYXbdfQtINyZwTVnijSPPBt++5nbBwGlXcFFcymoIEx6D9eIfJ+SdN+cHqwiyhkrI2gEpaiRV2oDDq7uUXp0dNnUDRMBZQAU+8sAjVQt4nXjU76RI+psuUWIOU1pgE8NQbJgFyzmjUsoKR9BUscqpTaBTWhDzl/cCjenIlHGlpsUp5MZkyH+12qHponmM5rB3bbnvQUAG1Ly4H5kBMPV2VI/fDQRPH190qC1VAb3O5BV6W";
		// cred.seteBayToken(input);
		cred.seteBayToken(token);

		// set Api Server Url
		// input = ConsoleUtil
		// .readString("Enter eBay SOAP server URL (Sandbox -> https://api.sandbox.ebay.com/wsapi): ");
		//
		input = "https://api.sandbox.ebay.com/wsapi";

		apiContext.setApiServerUrl(input);

		return apiContext;
	}

}
