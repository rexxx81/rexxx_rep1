package de.broo.test.ebay;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.call.AddItemCall;
import com.ebay.sdk.call.VerifyAddItemCall;
import com.ebay.sdk.helper.ConsoleUtil;
import com.ebay.sdk.util.eBayUtil;
import com.ebay.soap.eBLBaseComponents.AmountType;
import com.ebay.soap.eBLBaseComponents.BuyerPaymentMethodCodeType;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.ebay.soap.eBLBaseComponents.CountryCodeType;
import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
import com.ebay.soap.eBLBaseComponents.FeesType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.ListingDurationCodeType;
import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
import com.ebay.soap.eBLBaseComponents.NameValueListArrayType;
import com.ebay.soap.eBLBaseComponents.NameValueListType;
import com.ebay.soap.eBLBaseComponents.ReturnPolicyType;
import com.ebay.soap.eBLBaseComponents.ShippingDetailsType;
import com.ebay.soap.eBLBaseComponents.ShippingServiceCodeType;
import com.ebay.soap.eBLBaseComponents.ShippingServiceOptionsType;
import com.ebay.soap.eBLBaseComponents.ShippingTypeCodeType;
import com.ebay.soap.eBLBaseComponents.SiteCodeType;

import de.broo.test.swt.swtTest;

/**
 * 
 * A simple item listing sample, show basic flow to list an item to eBay Site
 * using eBay SDK.
 * 
 * @author boyang
 * 
 */
public class addItem {

	// sample category ids supporting custom item specifics
	// private static Set<String> sampleCatIDSet = new HashSet<String>();

	public static boolean festAuktion = true;
	public static double festpreis = 0;
	public static boolean zustandgebraucht = true;
	public static int angebotDauer = 7;
	public static String versand_deu = "";
	public static double versand_deu_preis = 0;
	public static String versand_welt = "";
	public static double versand_welt_preis = 0;
	public static boolean startzeit = false;
	public static Date startzeit_plan = null;
	// private static String[] pictureFiles = {
	// "C:\\Users\\Rexxx\\Dropbox\\Photos\\Export\\test\\titel.jpg" };
	private static String[] pictureFiles = { swtTest.selectedDir
			+ "\\titel.jpg" };
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd-HHmmss";

	public addItem() {

	}

	static {
		// sampleCatIDSet.add("162122");
		// sampleCatIDSet.add("162140");
		// sampleCatIDSet.add("30022");

		try {

			festAuktion = swtTest.festAuktion;
			festpreis = swtTest.festpreis;
			zustandgebraucht = swtTest.zustandgebraucht;
			angebotDauer = swtTest.angebotDauer;
			startzeit = swtTest.startzeit;
			startzeit_plan = swtTest.startzeit_plan;

			// [Step 1] Initialize eBay ApiContext object
			ApiContext apiContext = getApiContext();

			// [Step 2] Create a new item object.
			System.out.println(pictureFiles[0].toString());
			System.out.println("===== [2] Item Information ====");

			ItemType item = buildItem(swtTest.getTitel(),
					swtTest.getHTMLtemplate(), festpreis, angebotDauer);

			// [Step 3] Create call object and execute the call.
			System.out.println("===== [3] Execute the API call ====");
			System.out.println("Begin to call eBay API, please wait ...");

			try {

				VerifyAddItemCall verify = new VerifyAddItemCall(apiContext);
				verify.setSite(SiteCodeType.GERMANY);
				verify.setItem(item);
				FeesType fees = verify.verifyAddItem();

				AddItemCall api = new AddItemCall(apiContext);
				api.setSite(SiteCodeType.GERMANY);
				// api.setAutoSetItemUUID(true);
				api.setPictureFiles(pictureFiles);
				api.setItem(item);
				fees = api.addItem();
				System.out
						.println("End to call eBay API, show call result ...");
				System.out.println();

				// [Setp 4] Display results.
				System.out.println("The list was listed successfully!");

				double listingFee = eBayUtil
						.findFeeByName(fees.getFee(), "ListingFee").getFee()
						.getValue();
				System.out.println("Listing fee is: "
						+ new Double(listingFee).toString());
				System.out.println("Listed Item ID: " + item.getItemID());
			} catch (Exception e) {

				System.out.println("Item Check failed");
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.out.println("Fail to list the item.");
			e.printStackTrace();
		}

	}

	/**
	 * Build a sample item
	 * 
	 * @return ItemType object
	 */
	private static ItemType buildItem(String titel, String Description,
			double price, int days) throws IOException {

		// System.out.println(festAuktion + " " + festpreis + " "
		// + zustandgebraucht + " " + angebotDauer + " " + versand_deu
		// + " " + versand_deu_preis);

		String input;
		ItemType item = new ItemType();

		// item title
		item.setTitle(titel);
		// item description
		item.setDescription(Description);

		// festAuktion = swtTest.festAuktion;
		// festpreis = swtTest.festpreis;
		// zustandgebraucht = swtTest.zustandgebraucht;
		// angebotDauer = swtTest.angebotDauer;
		// versand_deu = swtTest.versand_deu;
		// versand_deu_preis = swtTest.versand_deu_preis;
		// versand_welt = swtTest.versand_welt;
		// versand_welt_preis = swtTest.versand_welt_preis;
		// startzeit = swtTest.startzeit;
		// startzeit_plan = swtTest.startzeit_plan;
		//

		// listing type
		if (festAuktion == true) {
			item.setListingType(ListingTypeCodeType.FIXED_PRICE_ITEM);
		} else {
			item.setListingType(ListingTypeCodeType.AUCTION);
		}

		// listing price
		item.setCurrency(CurrencyCodeType.EUR);
		AmountType amount = new AmountType();
		if (festAuktion == true) {
			amount.setValue(festpreis);
		} else {
			amount.setValue(1);
		}
		item.setStartPrice(amount);

		// listing duration
		// Days
		switch (angebotDauer) {

		case 5:
			item.setListingDuration(ListingDurationCodeType.DAYS_5.value());
			break;
		case 7:
			item.setListingDuration(ListingDurationCodeType.DAYS_7.value());
			break;
		case 10:
			item.setListingDuration(ListingDurationCodeType.DAYS_10.value());
			break;
		}

		// item location and country
		// item.setLocation(ConsoleUtil.readString("Location: "));
		item.setLocation("Ehringshausen");
		item.setCountry(CountryCodeType.DE);

		// listing category
		CategoryType cat = new CategoryType();
		// String inputString = "Primary Category (e.g.";
		// for (String catId : sampleCatIDSet) {
		// inputString = inputString + "," + catId;
		// }
		// inputString += "): ";
		// String catID = ConsoleUtil.readString(inputString);
		//
		cat.setCategoryID(swtTest.getCategorieID());
		System.out.println(swtTest.getCategorieID());
		item.setPrimaryCategory(cat);

		// item quantity
		item.setQuantity(new Integer(1));

		// payment methods
		item.setPaymentMethods(new BuyerPaymentMethodCodeType[] { BuyerPaymentMethodCodeType.PAY_PAL });
		item.setPayPalEmailAddress("info@antikgalerie24.de"); // email is
																// required if
		// paypal is used as payment
		// method

		// item condition, New

		if (zustandgebraucht == true) {
			item.setConditionID(3000);
		} else {
			item.setConditionID(1000);
		}

		// item specifics

		// if (sampleCatIDSet.contains(catID)) {
		// item.setItemSpecifics(buildItemSpecifics());
		// }

		// handling time is required
		item.setDispatchTimeMax(Integer.valueOf(4));

		// shipping details
		item.setShippingDetails(buildShippingDetails());

		// return policy
		ReturnPolicyType returnPolicy = new ReturnPolicyType();
		returnPolicy.setReturnsAcceptedOption("ReturnsAccepted");
		item.setReturnPolicy(returnPolicy);

		return item;
	}

	/**
	 * Populate eBay SDK ApiContext object with data input from user
	 * 
	 * @return ApiContext object
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
		apiContext.setEpsServerUrl("https://api.sandbox.ebay.com/ws/api.dll");

		return apiContext;
	}

	/**
	 * Build sample shipping details
	 * 
	 * @return ShippingDetailsType object
	 */
	private static ShippingDetailsType buildShippingDetails() {

		versand_deu = swtTest.versand_deu;
		versand_deu_preis = swtTest.versand_deu_preis;
		versand_welt = swtTest.versand_welt;
		versand_welt_preis = swtTest.versand_welt_preis;

		// Shipping details.
		ShippingDetailsType sd = new ShippingDetailsType();

		sd.setApplyShippingDiscount(new Boolean(true));
		AmountType amount = new AmountType();
		// amount.setValue(4.50);
		sd.setPaymentInstructions("");

		// Shipping type and shipping service options
		sd.setShippingType(ShippingTypeCodeType.FLAT);
		ShippingServiceOptionsType shippingOptions = new ShippingServiceOptionsType();
		shippingOptions
				.setShippingService(ShippingServiceCodeType.DE_HERMES_PAKET
						.value());
		amount = new AmountType();
		amount.setValue(4.9);
		// shippingOptions.setShippingServiceAdditionalCost(amount);
		// amount = new AmountType();
		// amount.setValue(10);
		shippingOptions.setShippingServiceCost(amount);
		shippingOptions.setShippingServicePriority(new Integer(1));
		amount = new AmountType();
		amount.setValue(0);
		shippingOptions.setShippingInsuranceCost(amount);

		sd.setShippingServiceOptions(new ShippingServiceOptionsType[] { shippingOptions });

		return sd;
	}

	// build sample item specifics
	public static NameValueListArrayType buildItemSpecifics() {

		// create the content of item specifics
		NameValueListArrayType nvArray = new NameValueListArrayType();
		NameValueListType nv1 = new NameValueListType();
		nv1.setName("Origin");
		nv1.setValue(new String[] { "US" });
		NameValueListType nv2 = new NameValueListType();
		nv2.setName("Year");
		nv2.setValue(new String[] { "2010" });
		nvArray.setNameValueList(new NameValueListType[] { nv1, nv2 });

		return nvArray;
	}

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());

	}
}
