package webservices;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dcube.com.salonseek.MyInfoActivity;
import utils.MyApplication;

public class WebServicesHandler {

    static MyApplication global;

    static ArrayList<HashMap<String, String>> salonList = new ArrayList<>();
    static ArrayList<HashMap<String, String>> signUpList = new ArrayList<>();
    static ArrayList<HashMap<String, String>> loginList = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> salonDetailList = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> normalTreatmentList = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> offerTreatmentList = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> stylistList = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> reviewList = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> BookedList = new ArrayList<HashMap<String, String>>();
    static ArrayList<HashMap<String, String>> treatmentlisting = new ArrayList<>();
    static ArrayList<HashMap<String, String>> onboardlisting = new ArrayList<>();
    static ArrayList<HashMap<String, String>> carddetaillist = new ArrayList<>();
    static ArrayList<HashMap<String, String>> given_reviews_list = new ArrayList<>();
    static ArrayList<HashMap<String, String>> fav_salon_list = new ArrayList<>();
    static ArrayList<HashMap<String, String>> salon_card_list = new ArrayList<>();
    static ArrayList<HashMap<String, String>> previously_visited_list = new ArrayList<>();


    static ArrayList<String> today_timings = new ArrayList<String>();
    static ArrayList<String> tomorrow_timings = new ArrayList<String>();
    static ArrayList<String> salonImageList = new ArrayList<>();
    static ArrayList<String> bookingList = new ArrayList<>();
    static ArrayList<String> availableStylist = new ArrayList<>();

    static HashMap<String, String> ProfileList = new HashMap<String, String>();


    //-------------------------(Salon Listing on map)----------------------------------//

    public static String SalonListingWebService(Context c) {
        String result = "error";
        String respage = "";
        String action = "";

        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.SALON_LIST_ON_MAP_URL);

            List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
            namepairs.add(new BasicNameValuePair("action", "salon_search"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Name PairSpace", "" + namepairs);

            try {
                salonList = new ArrayList<HashMap<String, String>>();
                respage = dehtt.execute(postMethod, res);
                Log.i("Restaurant List", "result is this" + respage);

                //JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = new JSONObject(respage);
                JSONArray array = obj1.getJSONArray("salons");

                if (array.isNull(0) || array.equals(null)) {
                    {
                        result = "false";
                    }
                } else {
                    for (int i = 0; i < array.length(); i++) {

                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject obj = array.getJSONObject(i);

                        String id = obj.getString(GlobalConstants.RES_ID);
                        String name = obj.getString(GlobalConstants.RES_NAME);
                        String icon = obj.getString(GlobalConstants.RES_ICON);
                        String address = obj.getString(GlobalConstants.RES_ADDRESS);
                        String lati = obj.getString(GlobalConstants.LATITUDE);
                        String longi = obj.getString(GlobalConstants.LONGITUDE);

                        JSONObject response = obj.getJSONObject(GlobalConstants.SALON_RATING);

                        String reviews = response.getString(GlobalConstants.SALON_REVIEWS);
                        String overall = response.getString(GlobalConstants.OVERALL_RATING);

                        JSONObject timings = obj.getJSONObject(GlobalConstants.SALON_TIMING_ARRAY);

                        String open_time = timings.getString(GlobalConstants.SALON_OPEN);
                        String close_time = timings.getString(GlobalConstants.SALON_CLOSED);

                        map.put(GlobalConstants.RES_ID, id);
                        map.put(GlobalConstants.RES_NAME, name);
                        map.put(GlobalConstants.RES_ICON, icon);
                        map.put(GlobalConstants.RES_ADDRESS, address);
                        map.put(GlobalConstants.LATITUDE, lati);
                        map.put(GlobalConstants.LONGITUDE, longi);
                        map.put(GlobalConstants.SALON_REVIEWS, reviews);
                        map.put(GlobalConstants.OVERALL_RATING, overall);
                        map.put(GlobalConstants.SALON_OPEN,open_time);
                        map.put(GlobalConstants.SALON_CLOSED,close_time);

                        salonList.add(map);

						/*for(int j= 0;j<response.length();j++) {

							String total_no_of_rating_reviews       =  obj.getString(GlobalConstants.SALON_REVIEWS);
							String overall_rating     =  obj.getString(GlobalConstants.OVERALL_RATING);

							Log.v("th" , total_no_of_rating_reviews);
						}*/
                    }

                    global.setSalonListing(salonList);
                    Log.i("Restaurant Listing", "" + global.getSalonListing());

                    result = "true";
                    global.setRun(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String SalonDetailsWebService(Context c, String salonID) {

        String result = "error";
        String respage = "";
        int isSalonfav = 0;

        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.SALON_LIST_ON_MAP_URL);

            List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
            namepairs.add(new BasicNameValuePair(GlobalConstants.RES_ID, salonID));
            namepairs.add(new BasicNameValuePair(GlobalConstants.SALON_CARD_USER_ID, global.getUser_id()));

            namepairs.add(new BasicNameValuePair("action", "get_salon_details_by_id"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Name PairSpace", "" + namepairs);

            try {

                salonDetailList = new ArrayList<>();
                salonImageList = new ArrayList<>();
                normalTreatmentList = new ArrayList<>();
                offerTreatmentList = new ArrayList<>();
                stylistList = new ArrayList<>();
                reviewList = new ArrayList<>();

                respage = dehtt.execute(postMethod, res);
                Log.i("Salon Detail List", "result is this" + respage);

                JSONObject obj2 = new JSONObject(respage);

                JSONObject obj1 = obj2.getJSONObject(GlobalConstants.SALON_DETAILS);

                HashMap<String, String> map = new HashMap<String, String>();

                String name = obj1.getString(GlobalConstants.SALON_NAME);
                String id = obj1.getString(GlobalConstants.SAL_ID);
                String contact = obj1.getString(GlobalConstants.SALON_CONTACT);
                String about = obj1.getString(GlobalConstants.SALON_ABOUT);
                String icon = obj1.getString(GlobalConstants.SALON_PROFILE_IMAGE);
                String address = obj1.getString(GlobalConstants.SALON_ADDRESS);
                String lati = obj1.getString(GlobalConstants.LATITUDE);
                String longi = obj1.getString(GlobalConstants.LONGITUDE);

                Log.v("salonanme ", name);

                String mon_from = obj1.getString(GlobalConstants.MON_FROM);
                String tue_from = obj1.getString(GlobalConstants.TUE_FROM);
                String wed_from = obj1.getString(GlobalConstants.WED_FROM);
                String thu_from = obj1.getString(GlobalConstants.THU_FROM);
                String fri_from = obj1.getString(GlobalConstants.FRI_FROM);
                String sat_from = obj1.getString(GlobalConstants.SAT_FROM);
                String sun_from = obj1.getString(GlobalConstants.SUN_FROM);

                String mon_to = obj1.getString(GlobalConstants.MON_TO);
                String tue_to = obj1.getString(GlobalConstants.TUE_TO);
                String wed_to = obj1.getString(GlobalConstants.WED_TO);
                String thu_to = obj1.getString(GlobalConstants.THU_TO);
                String fri_to = obj1.getString(GlobalConstants.FRI_TO);
                String sat_to = obj1.getString(GlobalConstants.SAT_TO);
                String sun_to = obj1.getString(GlobalConstants.SUN_TO);

                map.put(GlobalConstants.SALON_NAME, name);
                map.put(GlobalConstants.SAL_ID, id);
                map.put(GlobalConstants.SALON_CONTACT, contact);
                map.put(GlobalConstants.SALON_PROFILE_IMAGE, icon);
                map.put(GlobalConstants.SALON_ABOUT, about);
                map.put(GlobalConstants.SALON_ADDRESS, address);
                map.put(GlobalConstants.LATITUDE, lati);
                map.put(GlobalConstants.LONGITUDE, longi);

                map.put(GlobalConstants.MON_FROM, mon_from);
                map.put(GlobalConstants.TUE_FROM, tue_from);
                map.put(GlobalConstants.WED_FROM, wed_from);
                map.put(GlobalConstants.THU_FROM, thu_from);
                map.put(GlobalConstants.FRI_FROM, fri_from);
                map.put(GlobalConstants.SAT_FROM, sat_from);
                map.put(GlobalConstants.SUN_FROM, sun_from);

                map.put(GlobalConstants.MON_TO, mon_to);
                map.put(GlobalConstants.TUE_TO, tue_to);
                map.put(GlobalConstants.WED_TO, wed_to);
                map.put(GlobalConstants.THU_TO, thu_to);
                map.put(GlobalConstants.FRI_TO, fri_to);
                map.put(GlobalConstants.SAT_TO, sat_to);
                map.put(GlobalConstants.SUN_TO, sun_to);

                JSONObject response = obj2.getJSONObject(GlobalConstants.SALON_RATING);

                String reviews = response.getString(GlobalConstants.SALON_REVIEWS);
                String overall = response.getString(GlobalConstants.OVERALL_RATING);
                String rating1 = response.getString(GlobalConstants.RATING1);
                String rating2 = response.getString(GlobalConstants.RATING2);
                String rating3 = response.getString(GlobalConstants.RATING3);
                String rating4 = response.getString(GlobalConstants.RATING4);
                String rating5 = response.getString(GlobalConstants.RATING5);

                map.put(GlobalConstants.SALON_REVIEWS, reviews);
                map.put(GlobalConstants.OVERALL_RATING, overall);

                map.put(GlobalConstants.RATING1, rating1);
                map.put(GlobalConstants.RATING2, rating2);
                map.put(GlobalConstants.RATING3, rating3);
                map.put(GlobalConstants.RATING4, rating4);
                map.put(GlobalConstants.RATING5, rating5);

                salonDetailList.add(map);


                JSONArray salon_images = obj2.getJSONArray(GlobalConstants.SALON_IMAGES);

                for (int j = 0; j < salon_images.length(); j++)
                {

                    salonImageList.add(salon_images.getString(j));
                }

                try {

                    JSONObject treatment = obj2.getJSONObject(GlobalConstants.TREATMENT_PRICES);

                    JSONArray normal = treatment.getJSONArray(GlobalConstants.NORMAL);
                    JSONArray offer = treatment.getJSONArray(GlobalConstants.WITH_OFFER);

                    for (int j = 0; j < normal.length(); j++)
                    {
                        HashMap<String, String> normal_map = new HashMap<String, String>();

                        JSONObject normal_treatment = normal.getJSONObject(j);

                        String treatment_id = normal_treatment.getString(GlobalConstants.TREATMENT_ID);
                        String title = normal_treatment.getString(GlobalConstants.TITLE);
                        String desc = normal_treatment.getString(GlobalConstants.DESCRIPTION);
                        String price = normal_treatment.getString(GlobalConstants.PRICE);
                        String time = normal_treatment.getString(GlobalConstants.TIME);

                        normal_map.put(GlobalConstants.TREATMENT_ID, treatment_id);
                        normal_map.put(GlobalConstants.TITLE, title);
                        normal_map.put(GlobalConstants.DESCRIPTION, desc);
                        normal_map.put(GlobalConstants.PRICE, price);
                        normal_map.put(GlobalConstants.TIME, time);

                        normalTreatmentList.add(normal_map);
                    }

                    global.setNormalTreatmentListing(normalTreatmentList);

                    Log.v("normal treatment", global.getNormalTreatmentListing().toString());

                    for (int j = 0; j < offer.length(); j++)
                    {

                        HashMap<String, String> offer_map = new HashMap<String, String>();

                        JSONObject offer_treatment = offer.getJSONObject(j);

                        String treatment_id = offer_treatment.getString(GlobalConstants.TREATMENT_ID);
                        String title = offer_treatment.getString(GlobalConstants.TITLE);
                        String desc = offer_treatment.getString(GlobalConstants.DESCRIPTION);
                        String price = offer_treatment.getString(GlobalConstants.PRICE);
                        String time = offer_treatment.getString(GlobalConstants.TIME);

                        offer_map.put(GlobalConstants.TREATMENT_ID, treatment_id);
                        offer_map.put(GlobalConstants.TITLE, title);
                        offer_map.put(GlobalConstants.DESCRIPTION, desc);
                        offer_map.put(GlobalConstants.PRICE, price);
                        offer_map.put(GlobalConstants.TIME, time);

                        offerTreatmentList.add(offer_map);
                    }

                    global.setOfferTreatmentListing(offerTreatmentList);

                } catch (JSONException e) {

                    Log.v("error", e.toString());
                }
                try {

                    HashMap<String, String> default_stylist_map = new HashMap<String, String>();

                    default_stylist_map.put(GlobalConstants.STYLIST_NAME, "Any Stylist");
                    default_stylist_map.put(GlobalConstants.STYLIST_TYPE, "Any Stylist Available");
                    default_stylist_map.put(GlobalConstants.DESCRIPTION, "desc");
                    default_stylist_map.put(GlobalConstants.STYLIST_IMAGE, "");
                    default_stylist_map.put(GlobalConstants.STYLIST_ID, "0");

                    stylistList.add(default_stylist_map);

                    JSONArray stylist = obj2.getJSONArray(GlobalConstants.STYLIST);

                    for (int j = 0; j < stylist.length(); j++)
                    {

                        HashMap<String, String> stylist_map = new HashMap<String, String>();

                        JSONObject stylist_obj = stylist.getJSONObject(j);

                        String stylist_name = stylist_obj.getString(GlobalConstants.STYLIST_NAME);
                        String type = stylist_obj.getString(GlobalConstants.STYLIST_TYPE);
                        String desc = stylist_obj.getString(GlobalConstants.DESCRIPTION);
                        String offer = stylist_obj.getString(GlobalConstants.STYLIST_OFFER);
                        String image = stylist_obj.getString(GlobalConstants.STYLIST_IMAGE);
                        String stylist_id = stylist_obj.getString(GlobalConstants.STYLIST_ID);

                        stylist_map.put(GlobalConstants.STYLIST_NAME, stylist_name);
                        stylist_map.put(GlobalConstants.STYLIST_TYPE, type);
                        stylist_map.put(GlobalConstants.DESCRIPTION, desc);
                        stylist_map.put(GlobalConstants.STYLIST_OFFER, offer);
                        stylist_map.put(GlobalConstants.STYLIST_IMAGE, image);
                        stylist_map.put(GlobalConstants.STYLIST_ID, stylist_id);

                        stylistList.add(stylist_map);
                    }

                    global.setStylistListing(stylistList);

                } catch (JSONException e) {
                }

                try {

                    JSONArray review = obj2.getJSONArray(GlobalConstants.REVIEW_SALON);

                    for (int j = 0; j < review.length(); j++)
                    {

                        HashMap<String, String> review_map = new HashMap<String, String>();

                        JSONObject review_obj = review.getJSONObject(j);

                        String review_content = review_obj.getString(GlobalConstants.REVIEW_CONTENT);
                        String review_rating = review_obj.getString(GlobalConstants.REVIEW_RATING);
                        String reviewed_by = review_obj.getString(GlobalConstants.REVIEW_BY);
                        String created = review_obj.getString(GlobalConstants.REVIEW_CREATED);

                        review_map.put(GlobalConstants.REVIEW_CONTENT, review_content);
                        review_map.put(GlobalConstants.REVIEW_RATING, review_rating);
                        review_map.put(GlobalConstants.REVIEW_BY, reviewed_by);
                        review_map.put(GlobalConstants.REVIEW_CREATED, created);

                        reviewList.add(review_map);
                    }

                    global.setReviewListing(reviewList);

                } catch (JSONException e) {
                }

                global.setSalonDetailListing(salonDetailList);
                global.setSalonImageListing(salonImageList);

                try {

                    Log.e("Cards", "Cards SAlon");

                    salon_card_list.clear();

                    JSONArray jsonsaloncard = obj2.getJSONArray(GlobalConstants.SALON_CARD_ARRAY);

                    Log.e("JSonARRAy", "Array " + jsonsaloncard);

                    for (int i = 0; i <= jsonsaloncard.length(); i++)
                    {

                        HashMap<String, String> salon_card_map = new HashMap<String, String>();

                        JSONObject saloncardobj = jsonsaloncard.getJSONObject(i);

                        String card_id = saloncardobj.getString(GlobalConstants.SALON_CARD_ID);
                        String card_type = saloncardobj.getString(GlobalConstants.SALON_CARD_TYPE);
                        String card_user_id = saloncardobj.getString(GlobalConstants.SALON_CARD_USER_ID);
                        String card_is_active = saloncardobj.getString(GlobalConstants.SALON_CARD_IS_ACTIVE);
                        String card_salon_id = saloncardobj.getString(GlobalConstants.SALON_CARD_SALON_ID);

                        salon_card_map.put(GlobalConstants.SALON_CARD_ID, card_id);
                        salon_card_map.put(GlobalConstants.SALON_CARD_TYPE, card_type);
                        salon_card_map.put(GlobalConstants.SALON_CARD_USER_ID, card_user_id);
                        salon_card_map.put(GlobalConstants.SALON_CARD_IS_ACTIVE, card_is_active);
                        salon_card_map.put(GlobalConstants.SALON_CARD_SALON_ID, card_salon_id);

                        salon_card_list.add(salon_card_map);
                    }


                } catch (Exception e) {

                }

                global.setAl_salon_card(salon_card_list);


                try {

                    isSalonfav = obj2.optInt(GlobalConstants.IS_SALON_FAV);
                    Log.e("ISSalonFav", "" + obj2.optInt(GlobalConstants.IS_SALON_FAV));
                } catch (Exception e) {

                }

                global.setIs_salon_fav(isSalonfav);

                Log.e("ISSalonFav", "" + global.getIs_salon_fav());

                result = "true";

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //----------------------(Sign Up web service)--------------------------//
    public static String SignUpWebService(Context c, String username, String password, String email, String fullname) {

        String result = "error";
        String respage = "";
        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<NameValuePair>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.SIGN_UP_NAME, username));
            namepairs.add(new BasicNameValuePair(GlobalConstants.SIGN_UP_PASSWORD, password));
            namepairs.add(new BasicNameValuePair(GlobalConstants.SIGN_UP_EMAIL, email));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FULL_NAME, fullname));
            namepairs.add(new BasicNameValuePair(GlobalConstants.DEVICE_TYPE, "android"));
            namepairs.add(new BasicNameValuePair(GlobalConstants.DEVICE_TOKEN, global.getDevicetoken()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.NEWS_LETTER, "1"));

            namepairs.add(new BasicNameValuePair("action", "sign_up"));
            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Sign up Name PairSpace", "" + namepairs);

            try {
                signUpList = new ArrayList<HashMap<String, String>>();
                respage = dehtt.execute(postMethod, res);

                Log.i("Signup response", "result is this" + respage);

                JSONObject obj = new JSONObject(respage);

                String status = obj.getString(GlobalConstants.STATUS);

                HashMap<String, String> map = new HashMap<String, String>();
                String message = obj.getString(GlobalConstants.MESSAGE);

                if (status.equalsIgnoreCase("1")) {
                    {
                        map.put(GlobalConstants.MESSAGE, message);
                        global.setSignupListing(signUpList);

                        Log.i("sign up List ", "" + global.getSignupListing());
                        result = "true";
                    }
                } else {
                    map.put(GlobalConstants.MESSAGE, message);

                    global.setSignupListing(signUpList);
                    Log.i("sign up List", "" + global.getSignupListing());

                    result = "false";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }


    //----------------------------Login web service------------------------------//

    public static String LoginWebService(Context c, String email, String password) {

        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.LOGIN_EMAIL, email));
            namepairs.add(new BasicNameValuePair(GlobalConstants.LOGIN_PASSWORD, password));
            namepairs.add(new BasicNameValuePair(GlobalConstants.DEVICE_TYPE, "android"));
            namepairs.add(new BasicNameValuePair(GlobalConstants.DEVICE_TOKEN, global.getDevicetoken()));

            namepairs.add(new BasicNameValuePair("action", "login"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Login Name PairSpace", "" + namepairs);

            try {
                loginList = new ArrayList<HashMap<String, String>>();

                respage = dehtt.execute(postMethod, res);

                Log.i("Login response", "result is this" + respage);

                JSONObject obj = new JSONObject(respage);

                HashMap<String, String> map = new HashMap<String, String>();

                String status = obj.getString(GlobalConstants.STATUS);
                String message = obj.getString(GlobalConstants.MESSAGE);
                String id = obj.getString(GlobalConstants.USER_ID);

                String key_id = "";

                if (obj.getString(GlobalConstants.KEY_ID) == null)
                    key_id = obj.getString(GlobalConstants.KEY_ID);

                if (status.equalsIgnoreCase("1")) {
                    {

                        map.put(GlobalConstants.MESSAGE, message);
                        map.put(GlobalConstants.KEY_ID, key_id);
                        map.put(GlobalConstants.STATUS, status);
                        map.put(GlobalConstants.USER_ID, id);

                        loginList.add(map);

                        global.setLogin_email_id(email);

                        global.setLoginListing(loginList);

                        Log.i("Login List ", "" + global.getLoginListing());

                        result = "true";
                    }
                } else {
                    loginList.add(map);

                    global.setLoginListing(loginList);

                    Log.i("Login List", "" + global.getLoginListing());

                    result = "false";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;

    }


    public static String FacebookLoginWebService(Context c, String email, String fullname, String fb_id) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.FACEBOOK_ID, fb_id));
            namepairs.add(new BasicNameValuePair(GlobalConstants.LOGIN_EMAIL, email));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FULL_NAME, fullname));
            namepairs.add(new BasicNameValuePair(GlobalConstants.DEVICE_TYPE, "android"));
            namepairs.add(new BasicNameValuePair(GlobalConstants.DEVICE_TOKEN, global.getDevicetoken()));

            namepairs.add(new BasicNameValuePair("action", "fb_login"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Login Name PairSpace", "" + namepairs);

            try {
                loginList = new ArrayList<HashMap<String, String>>();

                respage = dehtt.execute(postMethod, res);

                Log.i("Login response", "result is this" + respage);

                JSONObject obj = new JSONObject(respage);

                HashMap<String, String> map = new HashMap<String, String>();

                String status = obj.getString(GlobalConstants.STATUS);
                String message = obj.getString(GlobalConstants.MESSAGE);
                String id = obj.getString(GlobalConstants.USER_ID);

                String key_id = "";

                if (obj.getString(GlobalConstants.KEY_ID) == null)
                    key_id = obj.getString(GlobalConstants.KEY_ID);

                if (status.equalsIgnoreCase("1")) {
                    {
                        map.put(GlobalConstants.MESSAGE, message);
                        map.put(GlobalConstants.KEY_ID, key_id);
                        map.put(GlobalConstants.STATUS, status);
                        map.put(GlobalConstants.USER_ID, id);

                        loginList.add(map);

                        global.setLoginListing(loginList);

                        Log.i("Login List ", "" + global.getLoginListing());

                        result = "true";
                    }
                } else {
                    loginList.add(map);

                    global.setLoginListing(loginList);
                    Log.i("Login List", "" + global.getLoginListing());

                    result = "false";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;

    }


    public static String GetSlotService(Context c, String salonID, String TreatmentID, String StylistID) {

        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        String treatment_id = "";

        if (global.getSelected_treatment_id().size() > 0) {

            treatment_id = TextUtils.join(",", global.getSelected_treatment_id());

            Log.e("Id", "Treatment " + treatment_id);

        }

        // global.getSpecial_selected_id().size() > 0
        if (global.isSpecialTreatmentSelected()) {

            String special_treatment = TextUtils.join(",", global.getSpecial_selected_id());

            Log.e("Id", "Special " + special_treatment);

            treatment_id = special_treatment + "," + treatment_id;

            Log.e("Id", "Treatment " + treatment_id);
        }


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_SALON_ID, salonID));
            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_TREATMENT_ID, treatment_id));
            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_STYLIST_ID, StylistID));

            namepairs.add(new BasicNameValuePair("action", "get_slot_to_book_salon"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Timing PairSpace", "" + namepairs);

            try {

                today_timings = new ArrayList<String>();
                tomorrow_timings = new ArrayList<String>();

                respage = dehtt.execute(postMethod, res);

                Log.i("Timing response", "result is this" + respage);

                JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = obj.getJSONObject("treatment");

                String status = obj.getString(GlobalConstants.STATUS);
                String message = obj.getString(GlobalConstants.MESSAGE);

                if (status.equalsIgnoreCase("1"))
                {
                    JSONArray today = obj1.getJSONArray(GlobalConstants.TODAY_TIME_SLOT);
                    JSONArray tomorrow = obj1.getJSONArray(GlobalConstants.TOMMOROW_TIME_SLOT);
                    int total_time = obj.optInt(GlobalConstants.BOOK_TOTAL_TIME);

                    if (today != null) {
                        for (int i = 0; i < today.length(); i++)
                        {
                            today_timings.add(today.get(i).toString());
                        }
                    }

                    if (tomorrow != null) {
                        for (int i = 0; i < tomorrow.length(); i++)
                        {
                            tomorrow_timings.add(tomorrow.get(i).toString());
                        }
                    }

                    global.setBook_total_time(total_time);

                    global.setTodayListing(today_timings);
                    global.setTomorrowListing(tomorrow_timings);

                    result = "true";

                } else {

                    result = "false";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;

    }


    public static String BookingService(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();


        try {

            String treatment_id = TextUtils.join(",", global.getSelected_treatment_id());

            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_SALON_ID, global.getBookingDetails().get(GlobalConstants.BOOK_SALON_ID)));
            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_TREATMENT_ID, treatment_id));
            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_STYLIST_ID, global.getBookingDetails().get(GlobalConstants.BOOK_STYLIST_ID)));
            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_USER_ID, global.getLoginListing().get(0).get(GlobalConstants.USER_ID)));
            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_DATE_TIME, global.getBookingTime()));

            //namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_STYLIST_ID,global.getBookingDetails().get(GlobalConstants.BOOK_STYLIST_ID)));

            namepairs.add(new BasicNameValuePair("action", "book_salon"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("booking PairSpace", "" + namepairs);

            try {
                bookingList = new ArrayList<String>();

                respage = dehtt.execute(postMethod, res);

                Log.i("booking response", "result is this" + respage);

                JSONObject obj = new JSONObject(respage);

                String status = obj.getString(GlobalConstants.STATUS);
                String message = obj.getString(GlobalConstants.MESSAGE);

                if (status.equalsIgnoreCase("1")) {
                    result = "true";
                    global.setBooking_status(1);


                } else {
                    result = "false";
                    global.setBooking_status(2);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;

    }

    public static String AvailableStylistService(Context c) {
        String result = "error";
        String respage = "";

        ArrayList<HashMap<String, String>> al_stylist = new ArrayList<HashMap<String, String>>();

        global = (MyApplication) c.getApplicationContext();


        try {

            String treatment_id = TextUtils.join(",", global.getSelected_treatment_id());

            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_SALON_ID, global.getBookingDetails().get(GlobalConstants.BOOK_SALON_ID)));
            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_TREATMENT_ID, treatment_id));

            //namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_STYLIST_ID,global.getBookingDetails().get(GlobalConstants.BOOK_STYLIST_ID)));

            namepairs.add(new BasicNameValuePair("action", "get_slot_to_book_salon_all_stylist"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("stylist PairSpace", "" + namepairs);

            try {

                availableStylist = new ArrayList<String>();

                respage = dehtt.execute(postMethod, res);

                Log.i("Stylists response", "result is this" + respage);

                JSONObject obj = new JSONObject(respage);

                String status = obj.getString(GlobalConstants.STATUS);
                String message = obj.getString(GlobalConstants.MESSAGE);

                if (status.equalsIgnoreCase("1"))
                {
                    JSONArray stylist = obj.getJSONArray(GlobalConstants.AVL_STYLIST_ARRAY);

                    JSONArray trtment = obj.getJSONArray(GlobalConstants.AVL_TRT_SLOTS);

                    for (int j = 0; j < stylist.length(); j++) {

                        HashMap<String, String> stylist_map = new HashMap<String, String>();

                        JSONObject stylist_obj = stylist.getJSONObject(j);

                        String stylist_id = stylist_obj.getString(GlobalConstants.AVL_STL_ID);
                        String stylist_name = stylist_obj.getString(GlobalConstants.AVL_STL_TITLE);
                        String type = stylist_obj.getString(GlobalConstants.AVL_STL_TYPE);
                        String desc = stylist_obj.getString(GlobalConstants.AVL_STL_DESCRIPTION);
                        String offer = stylist_obj.getString(GlobalConstants.AVL_STL_OFFER);
                        String image = stylist_obj.getString(GlobalConstants.AVL_STL_IMAGE);

                        stylist_map.put(GlobalConstants.AVL_STL_ID, stylist_id);
                        stylist_map.put(GlobalConstants.AVL_STL_TITLE, stylist_name);
                        stylist_map.put(GlobalConstants.AVL_STL_TYPE, type);
                        stylist_map.put(GlobalConstants.AVL_STL_DESCRIPTION, desc);
                        stylist_map.put(GlobalConstants.AVL_STL_OFFER, offer);
                        stylist_map.put(GlobalConstants.AVL_STL_IMAGE, image);

                        al_stylist.add(stylist_map);

                        JSONObject timing_obj = trtment.getJSONObject(j);

                        JSONArray today_time_slot = timing_obj.getJSONArray(GlobalConstants.AVL_TODAY_SLOTS);
                        JSONArray tomorrow_time_slot = timing_obj.getJSONArray(GlobalConstants.AVL_TOMORROW_SLOTS);

                        if (today_time_slot != null)
                        {
                            for (int k = 0; k < today_time_slot.length(); k++)
                            {
                                today_timings.add(today_time_slot.get(k).toString());
                            }
                        }

                        if (tomorrow_time_slot != null)
                        {
                            for (int l = 0; l < tomorrow_time_slot.length(); l++)
                            {
                                tomorrow_timings.add(tomorrow_time_slot.get(l).toString());
                            }
                        }

                    }



   /*
                    for(int i = 0 ; i <= trtment.length() ; i++)
                    {
                        JSONObject stylist_obj = trtment.getJSONObject(i);

                        JSONArray today_time_slot = stylist_obj.getJSONArray(GlobalConstants.AVL_TODAY_SLOTS);
                        JSONArray tomorrow_time_slot = stylist_obj.getJSONArray(GlobalConstants.AVL_TOMORROW_SLOTS);

//                        JSONArray today = obj1.getJSONArray(GlobalConstants.TODAY_TIME_SLOT);
//                        JSONArray tomorrow = obj1.getJSONArray(GlobalConstants.TOMMOROW_TIME_SLOT);
//                        int total_time = obj.optInt(GlobalConstants.BOOK_TOTAL_TIME);

                        if (today_time_slot != null) {
                            for (int k = 0; k < today_time_slot.length(); k++)
                            {
                                today_timings.add(today_time_slot.get(i).toString());
                            }
                        }

                        if (tomorrow_time_slot != null) {
                            for (int l = 0; l < tomorrow_time_slot.length(); l++)
                            {
                                tomorrow_timings.add(tomorrow_time_slot.get(i).toString());
                            }
                        }

                    }
*/
                    global.setStylistListing(al_stylist);

                    Log.i("WebServices","Stylist List Size"+al_stylist.size());

                    global.setTodayListing(today_timings);
                    global.setTomorrowListing(tomorrow_timings);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;

    }


    public static String GetBookingsService(Context c) {
        String result = "error";
        String respage = "";


        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);


            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_USER_ID, global.getLoginListing().get(0).get(GlobalConstants.USER_ID)));
            namepairs.add(new BasicNameValuePair("action", "get_my_bookings"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Booking PairSpace", "" + namepairs);

            try {

                BookedList = new ArrayList<>();

                respage = dehtt.execute(postMethod, res);

                Log.i("booking response", "result is this" + respage);

                JSONObject obj = new JSONObject(respage);

                String status = obj.optString("status");

                if (status.equalsIgnoreCase("1"))
                {
                    JSONArray array = obj.getJSONArray("bookings");

                    for (int i = 0; i < array.length(); i++) {

                        HashMap<String, String> map = new HashMap<String, String>();

                        JSONObject obj1 = array.getJSONObject(i);

                        String bookingid = obj1.getString(GlobalConstants.BOOKING_ID);
                        String datetime = obj1.getString(GlobalConstants.BOOK_DATE_TIME);
                        String salon_id = obj1.getString(GlobalConstants.BOOK_SALON_ID);
                        String salon_name = obj1.getString(GlobalConstants.BOOKED_SALON_NAME);
                        String salon_address = obj1.getString(GlobalConstants.BOOKED_SALON_ADDRESS);
                        String salon_profile_image = obj1.getString(GlobalConstants.BOOKED_SALON_PROFILE_IMAGE);
                        String lati = obj1.getString(GlobalConstants.BOOKED_SALON_LAT);
                        String longi = obj1.getString(GlobalConstants.BOOKED_SALON_LONG);
                        String stylist = obj1.getString(GlobalConstants.BOOKED_SALON_STYLIST);
                        String treatment_total = obj1.getString(GlobalConstants.BOOKED_TREATMENT_TOTAL);

                        JSONArray array1 = obj1.getJSONArray("treatments");

                        String treatments = "";

                        for (int j = 0; j < array1.length(); j++) {

                            treatments = array1.getString(j) + ",";
                        }

                        JSONObject response = obj1.getJSONObject(GlobalConstants.SALON_RATING);

                        String reviews = response.getString(GlobalConstants.SALON_REVIEWS);
                        String overall = response.getString(GlobalConstants.OVERALL_RATING);
                        String rating1 = response.getString(GlobalConstants.RATING1);
                        String rating2 = response.getString(GlobalConstants.RATING2);
                        String rating3 = response.getString(GlobalConstants.RATING3);
                        String rating4 = response.getString(GlobalConstants.RATING4);
                        String rating5 = response.getString(GlobalConstants.RATING5);

                        map.put(GlobalConstants.BOOKING_ID, bookingid);
                        map.put(GlobalConstants.BOOK_DATE_TIME, datetime);
                        map.put(GlobalConstants.BOOK_SALON_ID, salon_id);
                        map.put(GlobalConstants.BOOKED_SALON_NAME, salon_name);
                        map.put(GlobalConstants.BOOKED_SALON_ADDRESS, salon_address);
                        map.put(GlobalConstants.BOOKED_SALON_PROFILE_IMAGE, salon_profile_image);
                        map.put(GlobalConstants.BOOKED_SALON_LAT, lati);
                        map.put(GlobalConstants.BOOKED_SALON_LONG, longi);
                        map.put(GlobalConstants.BOOKED_SALON_STYLIST, stylist);
                        map.put(GlobalConstants.BOOKED_TREATMENT_TOTAL, treatment_total);
                        map.put(GlobalConstants.BOOKED_TREATMENTS, treatments);

                        map.put(GlobalConstants.SALON_REVIEWS, reviews);

                        map.put(GlobalConstants.OVERALL_RATING, overall);

                        map.put(GlobalConstants.RATING1, rating1);
                        map.put(GlobalConstants.RATING2, rating2);
                        map.put(GlobalConstants.RATING3, rating3);
                        map.put(GlobalConstants.RATING4, rating4);
                        map.put(GlobalConstants.RATING5, rating5);

                        BookedList.add(map);


                    }

                    global.setBookedListing(BookedList);
                    Log.i("BOOKED Listing", "" + global.getBookedListing());

                    result = "true";
                    global.setRun(false);

                }
                else
                {

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String CancelBooking(Context c, String booking_id) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOKING_ID, booking_id));

            namepairs.add(new BasicNameValuePair("action", "cancel_booking"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Restaurant List", "result is this" + respage);

                //JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = new JSONObject(respage);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String GetProfileService(Context c) {

        String result = "error";
        String respage = "";

        ArrayList<String> del_no;

        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.PROFILE_USER_ID, global.getLoginListing().get(0).get(GlobalConstants.USER_ID)));  //  global.getLoginListing().get(0).get(GlobalConstants.USER_ID)
            namepairs.add(new BasicNameValuePair("action", "get_profile"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            try {

                ProfileList = new HashMap<>();

                respage = dehtt.execute(postMethod, res);

                Log.i("profile response", "result is this" + respage);

                JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = obj.getJSONObject("user_data");

                String email = obj1.getString(GlobalConstants.LOGIN_EMAIL);
                String fullname = obj1.getString(GlobalConstants.FULL_NAME);
                String username = obj1.getString(GlobalConstants.USER_NAME);
                String user_dob = obj1.getString(GlobalConstants.USER_DOB);
                String user_img = obj1.getString(GlobalConstants.USER_IMG);

                JSONArray contact_array = obj1.getJSONArray(GlobalConstants.USER_CONTACT);

                del_no = new ArrayList<>();

                for (int i = 0; i < contact_array.length(); i++) {
                    del_no.add(contact_array.get(i).toString());
                }

                global.setUser_contact_no(del_no);

                ProfileList.put(GlobalConstants.LOGIN_EMAIL, email);
                ProfileList.put(GlobalConstants.FULL_NAME, fullname);
                ProfileList.put(GlobalConstants.USER_NAME, username);
                //        ProfileList.put(GlobalConstants.USER_CONTACT, del_no);
                ProfileList.put(GlobalConstants.USER_DOB, user_dob);
                ProfileList.put(GlobalConstants.USER_IMG, user_img);

                global.setProfileDetails(ProfileList);

                Log.i("profile Listing", "" + global.getProfileDetails());

                result = "true";

                global.setRun(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String FilterService(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        global.getSalonListing().clear();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.SORT_BY, global.getSortBy()));
      //      namepairs.add(new BasicNameValuePair(GlobalConstants.HIGHLOW, global.getHighLow()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_LAT, global.getFilterlat()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_LONG, global.getFilterlong()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_RATING, global.getFilterRating()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_DIS_FROM, global.getDistance_from()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_DIS_TO, global.getDistance_to()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_PRICE_FROM, global.getPrice_from()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_PRICE_TO, global.getPrice_to()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_POST_VISIT, global.getUser_id()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.FILTER_AVAILABLE, global.getAvailable()));

            namepairs.add(new BasicNameValuePair("action", "salon_filter"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            try {
                salonList = new ArrayList<HashMap<String, String>>();
                respage = dehtt.execute(postMethod, res);
                Log.i("Restaurant List", "result is this" + respage);

                //JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = new JSONObject(respage);

                String status = obj1.optString("status");

                if (status.equalsIgnoreCase("1"))
                {
                    JSONArray array = obj1.getJSONArray("salons");

                    if (array.isNull(0) || array.equals(null)) {
                        {
                            result = "false";
                        }
                    } else {
                        for (int i = 0; i < array.length(); i++)
                        {

                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject obj = array.getJSONObject(i);

                            String id = obj.getString(GlobalConstants.RES_ID);
                            String name = obj.getString(GlobalConstants.RES_NAME);
                            String icon = obj.getString(GlobalConstants.RES_ICON);
                            String address = obj.getString(GlobalConstants.RES_ADDRESS);
                            String lati = obj.getString(GlobalConstants.LATITUDE);
                            String longi = obj.getString(GlobalConstants.LONGITUDE);

                            JSONObject response = obj.getJSONObject(GlobalConstants.SALON_RATING);

                            String reviews = response.getString(GlobalConstants.SALON_REVIEWS);
                            String overall = response.getString(GlobalConstants.OVERALL_RATING);

                            JSONObject timings = obj.getJSONObject(GlobalConstants.SALON_TIMING_ARRAY);

                            String open_time = timings.getString(GlobalConstants.SALON_OPEN);
                            String close_time = timings.getString(GlobalConstants.SALON_CLOSED);

                            map.put(GlobalConstants.RES_ID, id);
                            map.put(GlobalConstants.RES_NAME, name);
                            map.put(GlobalConstants.RES_ICON, icon);
                            map.put(GlobalConstants.RES_ADDRESS, address);
                            map.put(GlobalConstants.LATITUDE, lati);
                            map.put(GlobalConstants.LONGITUDE, longi);
                            map.put(GlobalConstants.SALON_REVIEWS, reviews);
                            map.put(GlobalConstants.OVERALL_RATING, overall);
                            map.put(GlobalConstants.SALON_OPEN,open_time);
                            map.put(GlobalConstants.SALON_CLOSED,close_time);

                            salonList.add(map);
                        }

                        global.setSalonListing(salonList);
                        Log.i("Salon Filter Listing", "" + global.getSalonListing());

                        result = "true";
                        global.setRun(false);
                    }
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String SearchService(Context c, boolean keyword) {

        String result = "error";
        String respage = "";
        String selected_salon_id ="";
        String treatment_id ="";

        global = (MyApplication) c.getApplicationContext();

        salonList = new ArrayList<HashMap<String, String>>();
        salonList.clear();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            if (keyword)
            {
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_KeyWord, global.getSrc_KeyWord()));

            }
            else
            {

                if (global.al_search_salon_id.size() > 0) {

                    selected_salon_id = TextUtils.join(",", global.al_search_salon_id);

                    Log.i("Id", "Treatment " + selected_salon_id);

                }

                if (global.al_treatment_id.size() > 0)
                {
                    treatment_id = TextUtils.join(",", global.al_treatment_id);

                    Log.i("Id", "Treatment " + treatment_id);
                }


                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_LAT, global.getSrc_lat()));
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_LONG, global.getSrc_long()));
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_DATE_TIME, global.getSrc_date_time()));
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_TREATMENT_ID, treatment_id));  // str
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_SALON_ID, global.getSrc_salon_id()));  //selected_salon_id

            }

            namepairs.add(new BasicNameValuePair("action", "salon_search"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Salon List", "result is this" + respage);

                //JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = new JSONObject(respage);
                JSONArray array = obj1.getJSONArray("salons");

                if (array.isNull(0) || array.equals(null))
                {
                    result = "false";
                }
                else {
                    for (int i = 0; i < array.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject obj = array.getJSONObject(i);

                        String id = obj.getString(GlobalConstants.RES_ID);
                        String name = obj.getString(GlobalConstants.RES_NAME);
                        String icon = obj.getString(GlobalConstants.RES_ICON);
                        String address = obj.getString(GlobalConstants.RES_ADDRESS);
                        String lati = obj.getString(GlobalConstants.LATITUDE);
                        String longi = obj.getString(GlobalConstants.LONGITUDE);

                        JSONObject response = obj.getJSONObject(GlobalConstants.SALON_RATING);

                        String reviews = response.getString(GlobalConstants.SALON_REVIEWS);
                        String overall = response.getString(GlobalConstants.OVERALL_RATING);

                        map.put(GlobalConstants.RES_ID, id);
                        map.put(GlobalConstants.RES_NAME, name);
                        map.put(GlobalConstants.RES_ICON, icon);
                        map.put(GlobalConstants.RES_ADDRESS, address);
                        map.put(GlobalConstants.LATITUDE, lati);
                        map.put(GlobalConstants.LONGITUDE, longi);
                        map.put(GlobalConstants.SALON_REVIEWS, reviews);
                        map.put(GlobalConstants.OVERALL_RATING, overall);

                        salonList.add(map);
                    }

                    global.setSalonListing(salonList);
                    Log.i("Restaurant Listing", "" + global.getSalonListing());

                    result = "true";
                    global.setRun(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String SearchSlider(Context c, boolean keyword) {

        String result = "error";
        String respage = "";
        String selected_salon_id ="";
        String treatment_id ="";

        global = (MyApplication) c.getApplicationContext();

        salonList = new ArrayList<HashMap<String, String>>();
        salonList.clear();
        global.getSalonListing().clear();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            if (keyword)
            {
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_KeyWord, global.getSrc_KeyWord()));
            }
            else
            {
                if (global.al_search_salon_id.size() > 0) {

                    selected_salon_id = TextUtils.join(",", global.al_search_salon_id);

                    Log.i("Id", "Treatment " + selected_salon_id);
                }

                if (global.al_treatment_id.size() > 0)
                {
                    treatment_id = TextUtils.join(",", global.al_treatment_id);

                    Log.i("Id", "Treatment " + treatment_id);
                }

                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_LAT, global.getSrc_lat()));
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_LONG, global.getSrc_long()));
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_DATE_TIME,global.al_search_items.get(GlobalConstants.Search_DATE_TIME)));  //  global.getSrc_date_time()
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_TREATMENT_ID, treatment_id));  // str
                namepairs.add(new BasicNameValuePair(GlobalConstants.Search_SALON_ID,  global.al_search_items.get(GlobalConstants.Search_SALON_ID)));  //selected_salon_id

            }

            namepairs.add(new BasicNameValuePair("action", "salon_search"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Salon List", "result is this" + respage);

                //JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = new JSONObject(respage);
                String status = obj1.optString("status");

                if (status.equalsIgnoreCase("1"))   // array.isNull(0) || array.equals(null)
                {
                    JSONArray array = obj1.getJSONArray("salons");

                    for (int i = 0; i < array.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject obj = array.getJSONObject(i);

                        String id = obj.getString(GlobalConstants.RES_ID);
                        String name = obj.getString(GlobalConstants.RES_NAME);
                        String icon = obj.getString(GlobalConstants.RES_ICON);
                        String address = obj.getString(GlobalConstants.RES_ADDRESS);
                        String lati = obj.getString(GlobalConstants.LATITUDE);
                        String longi = obj.getString(GlobalConstants.LONGITUDE);

                        JSONObject response = obj.getJSONObject(GlobalConstants.SALON_RATING);

                        String reviews = response.getString(GlobalConstants.SALON_REVIEWS);
                        String overall = response.getString(GlobalConstants.OVERALL_RATING);

                        JSONObject timings = obj.getJSONObject(GlobalConstants.SALON_TIMING_ARRAY);

                        String open_time = timings.getString(GlobalConstants.SALON_OPEN);
                        String close_time = timings.getString(GlobalConstants.SALON_CLOSED);

                        map.put(GlobalConstants.RES_ID, id);
                        map.put(GlobalConstants.RES_NAME, name);
                        map.put(GlobalConstants.RES_ICON, icon);
                        map.put(GlobalConstants.RES_ADDRESS, address);
                        map.put(GlobalConstants.LATITUDE, lati);
                        map.put(GlobalConstants.LONGITUDE, longi);
                        map.put(GlobalConstants.SALON_REVIEWS, reviews);
                        map.put(GlobalConstants.OVERALL_RATING, overall);
                        map.put(GlobalConstants.SALON_OPEN,open_time);
                        map.put(GlobalConstants.SALON_CLOSED,close_time);

                        salonList.add(map);
                    }

                    global.setSalonListing(salonList);
                    Log.i("Restaurant Listing", "" + global.getSalonListing());

                    result = "true";
                    global.setRun(false);
                }
                else
                {
                   result = "false";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String AllTreatmentService(Context c) {

        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        treatmentlisting = new ArrayList<HashMap<String, String>>();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair("action", "treatment_search"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Restaurant List", "result is this" + respage);

                //JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = new JSONObject(respage);
                JSONArray array = obj1.getJSONArray("treatments");

                if (array.isNull(0) || array.equals(null)) {
                    {
                        result = "false";
                    }
                } else {

                    for (int i = 0; i < array.length(); i++) {

                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject obj = array.getJSONObject(i);

                        String id = obj.getString(GlobalConstants.TREATMENTS_ID);
                        String name = obj.getString(GlobalConstants.TREATMENTS_Name);
                        String desc = obj.getString(GlobalConstants.TREATMENTS_Desc);

                        map.put(GlobalConstants.TREATMENTS_ID, id);
                        map.put(GlobalConstants.TREATMENTS_Name, name);
                        map.put(GlobalConstants.TREATMENTS_Desc, desc);

                        Log.i("Id", "Id " + id);

                        treatmentlisting.add(map);
                    }

                    global.setTreatmentListing(treatmentlisting);
                    Log.i("Treatment Listing", "" + global.getTreatmentListing());

                    result = "true";
                    global.setRun(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String TreatmentService(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        treatmentlisting = new ArrayList<HashMap<String, String>>();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.Search_KeyWord, global.getSrc_KeyWord()));

            namepairs.add(new BasicNameValuePair("action", "treatment_search"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Restaurant List", "result is this" + respage);

                //JSONObject obj = new JSONObject(respage);
                JSONObject obj1 = new JSONObject(respage);
                JSONArray array = obj1.getJSONArray("treatments");

                if (array.isNull(0) || array.equals(null)) {
                    {
                        result = "false";
                    }
                } else {

                    for (int i = 0; i < array.length(); i++) {

                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject obj = array.getJSONObject(i);

                        String id = obj.getString(GlobalConstants.TREATMENTS_ID);
                        String name = obj.getString(GlobalConstants.TREATMENTS_Name);
                        String desc = obj.getString(GlobalConstants.TREATMENTS_Desc);

                        map.put(GlobalConstants.TREATMENTS_ID, id);
                        map.put(GlobalConstants.TREATMENTS_Name, name);
                        map.put(GlobalConstants.TREATMENTS_Desc, desc);

                        Log.i("Id", "Id " + id);

                        treatmentlisting.add(map);
                    }

                    global.setTreatmentListing(treatmentlisting);
                    Log.i("Treatment Listing", "" + global.getTreatmentListing());

                    result = "true";
                    global.setRun(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String SendToken(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        treatmentlisting = new ArrayList<HashMap<String, String>>();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.Stripe_Token_ID, global.getToken_id()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.PAYMENT_AMOUNT, global.getTotal_treatment_cost()));


            namepairs.add(new BasicNameValuePair("action", "set_token"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("STRIPE TOken", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


                Log.i("Token id", "Stripe TOkenId " + status);

                global.setPaymentStatus(status);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    public static String GetOnBoardScreens(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        onboardlisting = new ArrayList<HashMap<String, String>>();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair("action", "get_onboard_screens"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);

                JSONObject obj1 = new JSONObject(respage);
                JSONArray jsonArray = obj1.getJSONArray("onboard_screens");


                if (jsonArray.isNull(0) || jsonArray.equals(null)) {
                    {
                        result = "false";
                    }
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.optString(GlobalConstants.TITLE_OnBoard);
                        String description = jsonObject.optString(GlobalConstants.DESCRIPTION_OnBoard);
                        String photo = jsonObject.optString(GlobalConstants.PHOTO_OnBoard);


                        map.put(GlobalConstants.TITLE_OnBoard, title);
                        map.put(GlobalConstants.DESCRIPTION_OnBoard, description);
                        map.put(GlobalConstants.PHOTO_OnBoard, photo);

                        //		salonImageList.add(salon_images.getString(j));

                        onboardlisting.add(map);

                    }


                    global.setOnBoardListing(onboardlisting);


                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    public static String AddCard(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        onboardlisting = new ArrayList<HashMap<String, String>>();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.CARD_HOLDER, global.getCard_holder()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.Card_Number, global.getCard_no()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.CARD_Expiry, global.getCard_expiry()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.CARD_CVV, global.getCard_cvv()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.Card_type, global.getCard_type()));
            //    namepairs.add(new BasicNameValuePair(GlobalConstants.CardImg_Token, global.getCard_img_token()));

            namepairs.add(new BasicNameValuePair(GlobalConstants.Card_USER_id, global.getUser_id()));


            namepairs.add(new BasicNameValuePair("action", "Add_Card"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Add Card", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);

                String status = obj1.optString("status");
                String message = obj1.optString("msg");

                if (status.equalsIgnoreCase("1")) {
                    result = message;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    public static String FetchCardDetails(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        carddetaillist.clear();

        carddetaillist = new ArrayList<HashMap<String, String>>();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.Card_USER_id, global.getUser_id()));

            namepairs.add(new BasicNameValuePair("action", "Fetch_Cards"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Fetch_Cards", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);

                JSONArray jsonArray = obj1.getJSONArray("cards");

                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<>();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String id = jsonObject.optString(GlobalConstants.Card_id);
                    String card_name = jsonObject.optString(GlobalConstants.CARD_HOLDER);
                    String card_number = jsonObject.optString(GlobalConstants.Card_Number);
                    String card_expiry = jsonObject.optString(GlobalConstants.CARD_Expiry);
                    String cvv = jsonObject.optString(GlobalConstants.CARD_CVV);
                    String user_id = jsonObject.optString(GlobalConstants.Card_USER_id);
                    String card_type = jsonObject.optString(GlobalConstants.Card_type);


                    map.put(GlobalConstants.Card_id, id);
                    map.put(GlobalConstants.CARD_HOLDER, card_name);
                    map.put(GlobalConstants.Card_Number, card_number);
                    map.put(GlobalConstants.CARD_Expiry, card_expiry);
                    map.put(GlobalConstants.CARD_CVV, cvv);
                    map.put(GlobalConstants.Card_USER_id, user_id);
                    map.put(GlobalConstants.Card_type, card_type);

                    carddetaillist.add(map);

                }

                global.setAl_card_details(carddetaillist);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    public static String DeleteCard(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.Delete_Card_id, global.getCard_id()));

            namepairs.add(new BasicNameValuePair("action", "Delete_Card"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Delete Card", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String FetchGivenReviews(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        given_reviews_list = new ArrayList<HashMap<String, String>>();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_USER_ID, global.getLoginListing().get(0).get(GlobalConstants.USER_ID)));

            namepairs.add(new BasicNameValuePair("action", "given_review"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Fetch_reviews", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);

                JSONArray jsonArray = obj1.getJSONArray("giv_rev");

                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<>();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String id = jsonObject.optString(GlobalConstants.REVIEW_ID);
                    String user_id = jsonObject.optString(GlobalConstants.REVIEW_USER_ID);
                    String salon_name = jsonObject.optString(GlobalConstants.REVIEW_SALON_NAME);
                    String rating = jsonObject.optString(GlobalConstants.REVIEW_RAT);
                    String reviewlabel = jsonObject.optString(GlobalConstants.REVIEW_LABEL);
                    String review_created = jsonObject.optString(GlobalConstants.REVIEW_CREATE);


                    map.put(GlobalConstants.REVIEW_ID, id);
                    map.put(GlobalConstants.REVIEW_USER_ID, user_id);
                    map.put(GlobalConstants.REVIEW_SALON_NAME, salon_name);
                    map.put(GlobalConstants.REVIEW_RAT, rating);
                    map.put(GlobalConstants.REVIEW_LABEL, reviewlabel);
                    map.put(GlobalConstants.REVIEW_CREATE, review_created);

                    given_reviews_list.add(map);

                }

                global.setAl_given_review(given_reviews_list);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    public static String DeleteReview(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair("review_id", global.getReview_id()));

            namepairs.add(new BasicNameValuePair("action", "delete_review"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Delete Review", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String GetFavoriteSalon(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        global.getAl_fav_salon().clear();

        String user_id = global.getLoginListing().get(0).get(GlobalConstants.USER_ID);

        fav_salon_list = new ArrayList<HashMap<String, String>>();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_USER_ID, user_id));

            namepairs.add(new BasicNameValuePair("action", "get_favourite_salons"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("FavoriteSalon", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                String status = obj1.optString(GlobalConstants.STATUS);

                if (status.equalsIgnoreCase("1"))
                {
                    JSONArray jsonArray = obj1.getJSONArray("salons");

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        HashMap<String, String> map = new HashMap<>();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.optString(GlobalConstants.FAV_SALON_ID);
                        String img_url = jsonObject.optString(GlobalConstants.FAV_SALON_IMG);
                        String salon_name = jsonObject.optString(GlobalConstants.FAV_SALON_NAME);
                        String address = jsonObject.optString(GlobalConstants.FAV_SALON_ADDRESS);
                        String latitude = jsonObject.optString(GlobalConstants.FAV_SALON_LAT);
                        String longitude = jsonObject.optString(GlobalConstants.FAV_SALON_LONG);
                        String contact = jsonObject.optString(GlobalConstants.FAV_SALON_CONTACT);
                        String total_rating = jsonObject.optString(GlobalConstants.FAV_SALON_TOTAL_RATING);
                        String overall = jsonObject.optString(GlobalConstants.FAV_SALON_OVERALL_RATING);
                        String rating1 = jsonObject.optString(GlobalConstants.FAV_SALON_RAT_1);
                        String rating2 = jsonObject.optString(GlobalConstants.FAV_SALON_RAT_2);
                        String rating3 = jsonObject.optString(GlobalConstants.FAV_SALON_RAT_3);
                        String rating4 = jsonObject.optString(GlobalConstants.FAV_SALON_RAT_4);
                        String rating5 = jsonObject.optString(GlobalConstants.FAV_SALON_RAT_5);


                        map.put(GlobalConstants.FAV_SALON_ID, id);
                        map.put(GlobalConstants.FAV_SALON_IMG, img_url);
                        map.put(GlobalConstants.FAV_SALON_NAME, salon_name);
                        map.put(GlobalConstants.FAV_SALON_ADDRESS, address);
                        map.put(GlobalConstants.FAV_SALON_LAT, latitude);
                        map.put(GlobalConstants.FAV_SALON_LONG, longitude);
                        map.put(GlobalConstants.FAV_SALON_CONTACT, contact);
                        map.put(GlobalConstants.FAV_SALON_TOTAL_RATING, total_rating);
                        map.put(GlobalConstants.FAV_SALON_OVERALL_RATING, overall);
                        map.put(GlobalConstants.FAV_SALON_RAT_1, rating1);
                        map.put(GlobalConstants.FAV_SALON_RAT_2, rating2);
                        map.put(GlobalConstants.FAV_SALON_RAT_3, rating3);
                        map.put(GlobalConstants.FAV_SALON_RAT_4, rating4);
                        map.put(GlobalConstants.FAV_SALON_RAT_5, rating5);

                        fav_salon_list.add(map);

                    }

                    global.setAl_fav_salon(fav_salon_list);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String MakeSalonFav(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.MAKE_FAV_SALON_USER_ID, global.getUser_id()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.MAKE_FAV_SALON_ID, global.getMake_salon_fav_id()));

            namepairs.add(new BasicNameValuePair("action", "make_salon_favourite"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Delete Review", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


            } catch (Exception e) {
                e.printStackTrace();
            }

            global.setIs_salon_fav(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String MakeSalonUnFav(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.MAKE_FAV_SALON_USER_ID, global.getUser_id()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.MAKE_FAV_SALON_ID, global.getMake_salon_fav_id()));

            namepairs.add(new BasicNameValuePair("action", "make_salon_unfavourite"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Delete Review", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


            } catch (Exception e) {
                e.printStackTrace();
            }

            global.setIs_salon_fav(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String UpdateProfile(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.PROFILE_USER_ID, global.getUser_id()));  //global.getUser_id()
            namepairs.add(new BasicNameValuePair(GlobalConstants.PROFILE_PWD, global.getPassword()));

            if (MyInfoActivity.is_pic_changed)
            {
                namepairs.add(new BasicNameValuePair("device_type", "android"));
                namepairs.add(new BasicNameValuePair(GlobalConstants.PROFILE_IMAGE, MyInfoActivity.data.get(MyInfoActivity.UPLOAD_KEY)));
            }

            namepairs.add(new BasicNameValuePair("action", "update_profile_dp_android"));  //update_profile

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("update_profile", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String AddNumberToUser(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.PROFILE_USER_ID, global.getUser_id()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.PROFILE_ADD_NUMBER, global.getAdd_number()));

            namepairs.add(new BasicNameValuePair("action", "more_number"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Add Number", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String DeleteNumberOfUser(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.PROFILE_USER_ID, global.getUser_id()));
            namepairs.add(new BasicNameValuePair(GlobalConstants.PROFILE_DELETE_NUMBER, global.getDelete_number()));

            namepairs.add(new BasicNameValuePair("action", "delete_number"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Delete Number", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String SendSMS(Context c) {
        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();


        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.SMS_TO, "+918195927482"));
            namepairs.add(new BasicNameValuePair(GlobalConstants.SMS_MESSAGE, "Rohit Testing"));

            namepairs.add(new BasicNameValuePair("action", "send_sms"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);
                Log.i("Delete Number", "result is this" + respage);

                JSONObject obj1 = new JSONObject(respage);
                int status = obj1.getInt("status");


            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static String PreviouslyVisited(Context c) {

        String result = "error";
        String respage = "";

        global = (MyApplication) c.getApplicationContext();

        String user_id = global.getLoginListing().get(0).get(GlobalConstants.USER_ID);

        previously_visited_list = new ArrayList<HashMap<String, String>>();

        try {
            DefaultHttpClient dehtt = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(GlobalConstants.URL);

            List<NameValuePair> namepairs = new ArrayList<>();

            namepairs.add(new BasicNameValuePair(GlobalConstants.BOOK_USER_ID, user_id));

            namepairs.add(new BasicNameValuePair("action", "prev_visited"));

            postMethod.setEntity(new UrlEncodedFormEntity(namepairs));

            Log.i("Get Profile PairSpace", "" + namepairs);

            Log.i("Name PairSpace", "" + namepairs);

            try {

                respage = dehtt.execute(postMethod, res);

                Log.i("Fetch_Cards", "result is this" + respage);
                JSONObject obj1 = new JSONObject(respage);

                String status = obj1.optString("status");

                if (status.equalsIgnoreCase("1"))
                {
                    JSONArray jsonArray = obj1.getJSONArray("salons");

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        HashMap<String, String> map = new HashMap<>();

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.optString(GlobalConstants.PRE_SALON_ID);
                        String img_url = jsonObject.optString(GlobalConstants.PRE_SALON_IMG);
                        String salon_name = jsonObject.optString(GlobalConstants.PRE_SALON_NAME);
                        String address = jsonObject.optString(GlobalConstants.PRE_SALON_ADDRESS);
                        String latitude = jsonObject.optString(GlobalConstants.PRE_SALON_LAT);
                        String longitude = jsonObject.optString(GlobalConstants.PRE_SALON_LONG);
                        String contact = jsonObject.optString(GlobalConstants.PRE_SALON_CONTACT);
                        String total_rating = jsonObject.optString(GlobalConstants.PRE_SALON_TOTAL_RATING);
                        String overall = jsonObject.optString(GlobalConstants.PRE_SALON_OVERALL_RATING);
                        String rating1 = jsonObject.optString(GlobalConstants.PRE_SALON_RAT_1);
                        String rating2 = jsonObject.optString(GlobalConstants.PRE_SALON_RAT_2);
                        String rating3 = jsonObject.optString(GlobalConstants.PRE_SALON_RAT_3);
                        String rating4 = jsonObject.optString(GlobalConstants.PRE_SALON_RAT_4);
                        String rating5 = jsonObject.optString(GlobalConstants.PRE_SALON_RAT_5);


                        map.put(GlobalConstants.PRE_SALON_ID, id);
                        map.put(GlobalConstants.PRE_SALON_IMG, img_url);
                        map.put(GlobalConstants.PRE_SALON_NAME, salon_name);
                        map.put(GlobalConstants.PRE_SALON_ADDRESS, address);
                        map.put(GlobalConstants.PRE_SALON_LAT, latitude);
                        map.put(GlobalConstants.PRE_SALON_LONG, longitude);
                        map.put(GlobalConstants.PRE_SALON_CONTACT, contact);
                        map.put(GlobalConstants.PRE_SALON_TOTAL_RATING, total_rating);
                        map.put(GlobalConstants.PRE_SALON_OVERALL_RATING, overall);
                        map.put(GlobalConstants.PRE_SALON_RAT_1, rating1);
                        map.put(GlobalConstants.PRE_SALON_RAT_2, rating2);
                        map.put(GlobalConstants.PRE_SALON_RAT_3, rating3);
                        map.put(GlobalConstants.PRE_SALON_RAT_4, rating4);
                        map.put(GlobalConstants.PRE_SALON_RAT_5, rating5);

                        previously_visited_list.add(map);

                    }

                    global.setAl_prev_visit(previously_visited_list);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    //----------------------------Post a Review web service------------------------------//
     
	/*public static String PostReviewWebService(Context c,String res_id,String email,String comment,String type)

	{
		
		String result  = "error";
		String respage =  "";
		
		 	
		global = (MyApplication) c.getApplicationContext();
		
		try 
		 {
		   	DefaultHttpClient dehtt       = new DefaultHttpClient();
			ResponseHandler<String> res   = new BasicResponseHandler();
			HttpPost postMethod           = new HttpPost(GlobalConstants.URL);
			
			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
			 
		 	namepairs.add(new BasicNameValuePair(GlobalConstants.RES_ID,res_id));
			namepairs.add(new BasicNameValuePair(GlobalConstants.REVIEW_LOGIN,email));
			namepairs.add(new BasicNameValuePair(GlobalConstants.REVIEW_CONTENT,comment));
			namepairs.add(new BasicNameValuePair(GlobalConstants.REVIEW_TYPE,type));
			
			namepairs.add(new BasicNameValuePair("reqtype","addreview"));
	
			postMethod.setEntity(new UrlEncodedFormEntity(namepairs));
			
			Log.i("Review Name PairSpace",""+namepairs);
			
			try 
			 {
				 	reviewList =  new ArrayList<HashMap<String,String>>();
	                
					respage    =  dehtt.execute(postMethod,res);
	               
					Log.i("Login response","result is this"+respage);
					
					JSONObject obj   =  new JSONObject(respage);
					
					HashMap<String, String> map = new HashMap<String, String>();
					
					String status  =  obj.getString(GlobalConstants.STATUS);
					
					String message = obj.getString(GlobalConstants.MESSAGE);
					 	
					if(status.equalsIgnoreCase("True"))
					{
					     {
						    	
						 map.put(GlobalConstants.MESSAGE, message);
						 
					 	 map.put(GlobalConstants.STATUS, status);
						 
						 reviewList.add(map);
						 
						 global.setReviewListing(reviewList);
					       
					     Log.i("Review List",""+ global.getReviewListing());
								
					     result="true";
					     }
					}
				else
				{ 	
					reviewList.add(map);
					
					global.setReviewListing(reviewList);
		
					Log.i("Review  List",""+ global.getLoginListing());
					result = "false";
				
				}
				
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			
				} catch (Exception e) {
					
					e.printStackTrace();
				}
		
		   return result;
   
	 }  
      
  //----------------------------Review Feedback web service------------------------------//
 	
   public static String ReviewFeedBackWebService(Context c,String res_id , String type)
  	 {
	   
  		String result  = "error";
  		String respage =  "";
  		
  		global = (MyApplication) c.getApplicationContext();
  		
  		try 
  		 {
  		   	DefaultHttpClient dehtt       = new DefaultHttpClient();
  			ResponseHandler<String> res   = new BasicResponseHandler();
  			HttpPost postMethod           = new HttpPost(GlobalConstants.URL);
  			
  			List<NameValuePair> namepairs = new ArrayList<NameValuePair>();
  			namepairs.add(new BasicNameValuePair(GlobalConstants.RES_ID,res_id));
  			
  		 	if(type=="1")
  		 		
  			namepairs.add(new BasicNameValuePair("reqtype","goodreview"));
  	
  		 	else
  		
  		    if(type=="0")
  					
  			namepairs.add(new BasicNameValuePair("reqtype","badreview"));
  	  		
  			postMethod.setEntity(new UrlEncodedFormEntity(namepairs));
  			
  			Log.i("Feedback Name PairSpace" , "" +namepairs);
  			
  			
  			try 
		     {
			
  			   feedbacklist =  new ArrayList<HashMap<String,String>>();
              
  			   respage =  dehtt.execute(postMethod,res);
               Log.i("feedback List response ","result is this"+respage);
               
				JSONArray array =  new JSONArray(respage);
				if(array.isNull(0) || array.equals(null))
				{
				         {
							result="false";
						 }
			 	   }
  		 		else
				
				{
						for(int i= 0;i<array.length();i++)
						{
						 	HashMap<String, String> map = new HashMap<String, String>();
							
							JSONObject obj = array.getJSONObject(i);
							
							String review       =  obj.getString(GlobalConstants.FEEDBACK);
							String date     =  obj.getString(GlobalConstants.FEEDBACK_DATE);
						 	
							map.put(GlobalConstants.FEEDBACK, review);
							map.put(GlobalConstants.FEEDBACK_DATE,date);
		 
							feedbacklist.add(map);
				 			
						}

						global.setfeedbackListing(feedbacklist);
						
					  Log.i("review List :--",""+global.getfeedbackListing());	 
							  
							  result = "true";
			 	}
			  	
  				} catch (Exception e) {
  					e.printStackTrace();
  				}
  			
  				} catch (Exception e) {
  					
  					e.printStackTrace();
  				}
  		    return result;
  	 }*/


}
