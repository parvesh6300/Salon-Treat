package webservices;

public class GlobalConstants {

    public static final String SALON_IMAGES_URL = "http://salontreat.com/admin/uploads/original/";   //http://dcubetechnologies.com/salonadvisor/admin/uploads/original/

    public static final String SALON_IMAGES_THUMBNAIL_URL = "http://salontreat.com/admin/uploads/thumbnail/";  //http://dcubetechnologies.com/salonadvisor/admin/uploads/thumbnail/

    //------------------Constants for Login Cookies------------------------//

    public static final String LOGGED_IN_USER = "Email";

    //------------------Constants for Login----------------------------------//

    public static final String STATUS = "status";

    public static final String MESSAGE = "msg";

    public static final String USER_ID = "id";

    public static final String KEY_ID = "status";

    //------------------Constants for map view web service------------------------//

    public static final String SALON_LIST_ON_MAP_URL = "http://salontreat.com/admin/api/webservice.php";

    //----Input data

    public static final String INPUT_LATITUDE = "latitude";

    public static final String INPUT_LONGITUDE = "longitude";

    //----Display data

    public static final String RES_ID = "salon_id";
    public static final String RES_ICON = "profile_image";
    public static final String RES_NAME = "salon_name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String RES_ADDRESS = "address";
    public static final String SALON_RATING = "salon_rating";
    public static final String SALON_REVIEWS = "total_no_of_rating_reviews";
    public static final String OVERALL_RATING = "overall_rating";
    public static final String SALON_TIMING_ARRAY = "war";
    public static final String SALON_OPEN = "start_time";
    public static final String SALON_CLOSED = "end_time";


    //---------------------------Constants for Salon Details------------------------------//

    public static final String SALON_DETAILS = "salon_details";
    public static final String SALON_NAME = "name";
    public static final String SALON_ID = "salon_id";
    public static final String SAL_ID = "id";

    public static final String SALON_CONTACT = "contact";
    public static final String SALON_ABOUT = "about";
    public static final String SALON_ADDRESS = "address";
    public static final String SALON_PROFILE_IMAGE = "profile_image";

    public static final String MON_FROM = "trading_hour_mon_from";
    public static final String TUE_FROM = "trading_hour_tue_from";
    public static final String WED_FROM = "trading_hour_wed_from";
    public static final String THU_FROM = "trading_hour_thu_from";
    public static final String FRI_FROM = "trading_hour_fri_from";
    public static final String SAT_FROM = "trading_hour_sat_from";
    public static final String SUN_FROM = "trading_hour_sun_from";

    public static final String MON_TO = "trading_hour_mon_to";
    public static final String TUE_TO = "trading_hour_tue_to";
    public static final String WED_TO = "trading_hour_wed_to";
    public static final String THU_TO = "trading_hour_thu_to";
    public static final String FRI_TO = "trading_hour_fri_to";
    public static final String SAT_TO = "trading_hour_sat_to";
    public static final String SUN_TO = "trading_hour_sun_to";

    public static final String RATING1 = "rating1";
    public static final String RATING2 = "rating2";
    public static final String RATING3 = "rating3";
    public static final String RATING4 = "rating4";
    public static final String RATING5 = "rating5";

    public static final String TREATMENT_PRICES = "salon_treatment_prices";
    public static final String NORMAL = "normal";
    public static final String WITH_OFFER = "with_offer";
    public static final String TREATMENT_ID = "treatment_id";

    public static final String TITLE = "title";
    public static final String PRICE = "price";
    public static final String TIME = "time";

    public static final String DESCRIPTION = "description";
    public static final String SALON_IMAGES = "salon_images";

    public static final String SALON_REVIEW_LIST = "salon_reviews";

    public static final String STYLIST = "salon_stylist";
    public static final String STYLIST_ID = "id";
    public static final String STYLIST_TYPE = "stylist_type";
    public static final String STYLIST_NAME = "title";
    public static final String STYLIST_IMAGE = "image";
    public static final String STYLIST_OFFER = "offer";


    public static final String SALON_CARD_ARRAY = "cards_by_salon";
    public static final String SALON_CARD_ID = "id";
    public static final String SALON_CARD_TYPE = "card_type";
    public static final String SALON_CARD_USER_ID = "user_id";
    public static final String SALON_CARD_IS_ACTIVE = "is_active";
    public static final String SALON_CARD_SALON_ID = "salon_id";


    //---------------------------Constants for Sign up------------------------------//

    public static final String URL = "http://salontreat.com/admin/api/webservice.php"; //dcubetechnologies.com/salonseek/admin/api/webservice.php

    public static final String SIGN_UP_NAME = "username";

    public static final String SIGN_UP_EMAIL = "email";

    public static final String SIGN_UP_PASSWORD = "password";

    public static final String SIGN_UP_PHONE = "phone";

    //---------------------Constants for Login------------------------------------//

    public static final String LOGIN_EMAIL = "email";

    public static final String LOGIN_PASSWORD = "password";

    public static final String DEVICE_TYPE = "devic_type";

    public static final String DEVICE_TOKEN = "device_token";

    public static final String NEWS_LETTER = "is_subscribe_newsletter";

    public static final String FACEBOOK_ID = "fb_id";

    public static final String FULL_NAME = "fullname";
    public static final String USER_NAME = "username";
    public static final String USER_CONTACT = "contact";
    public static final String USER_DOB = "dob";
    public static final String USER_IMG = "profile_image";


    //---------------------Constants for Review--------------------------------//

    public static final String REVIEW_SALON = "salon_reviews";
    public static final String REVIEW_RATING = "rating";
    public static final String REVIEW_CONTENT = "review";
    public static final String REVIEW_BY = "reviewed_by";
    public static final String REVIEW_CREATED = "created";

    //---------------------Constants for Booking--------------------------------//

    public static final String BOOK_USER_ID = "user_id";
    public static final String BOOK_SALON_ID = "salon_id";
    public static final String BOOK_TREATMENT_ID = "treatment_ids";
    public static final String BOOK_STYLIST_ID = "stylist_id";
    public static final String BOOK_DATE_TIME = "booking_datetime";
    public static final String BOOK_TOTAL_TIME = "total_time";

    public static final String BOOKING_ID = "booking_id";
    public static final String BOOKED_SALON_NAME = "salon_name";
    public static final String BOOKED_SALON_PROFILE_IMAGE = "salon_profile_image";
    public static final String BOOKED_SALON_ADDRESS = "salon_address";
    public static final String BOOKED_SALON_LAT = "salon_latitude";
    public static final String BOOKED_SALON_LONG = "salon_longitude";
    public static final String BOOKED_SALON_STYLIST = "stylist";
    public static final String BOOKED_TREATMENTS = "treatments";

    public static final String BOOKED_TREATMENT_TOTAL = "treatment_total";

    public static final String TODAY_TIME_SLOT = "today_time_slots";
    public static final String TOMMOROW_TIME_SLOT = "tomorrow_time_slots";

    public static final String PAYMENT_GATEWAY = "payment_gateway";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String AMOUNT_PAID = "amount_paid";


    //---------------------Constants for Filter Search--------------------------------//

    public static final String SORT_BY = "sortBY";
    public static final String HIGHLOW = "highlow";
    public static final String FILTER_LAT = "latitude";
    public static final String FILTER_LONG = "longitude";
    public static final String FILTER_DATE = "datetime";
    public static final String FILTER_RATING = "RatingNo";

    public static final String FILTER_DIS_FROM = "distanceKmfrom";
    public static final String FILTER_DIS_TO = "distanceKmupto";
    public static final String FILTER_PRICE_FROM = "pricefrom";
    public static final String FILTER_PRICE_TO = "priceto";
    public static final String FILTER_AVAILABLE = "available";
    public static final String FILTER_POST_VISIT = "post_visit";


    //---------------------Constants for SAlon Search--------------------------------//


    public static final String Search_KeyWord = "keyword";
    public static final String Search_LAT = "latitude";
    public static final String Search_LONG = "longitude";
    public static final String Search_DATE_TIME = "datetime";
    public static final String Search_TREATMENT_ID = "treatment_id";
    public static final String Search_SALON_ID = "salon_type_id";
    public static final String Search_Salon_Name = "salon_name";
    public static final String Search_Place_Name = "place_name";
    public static final String Search_Location = "location_name";


    //---------------------Constants for Treatment Search--------------------------------//


    public static final String TREATMENTS_ID = "id";
    public static final String TREATMENTS_Name = "name";
    public static final String TREATMENTS_Desc = "Desc";


    //---------------------Constants for Stripe Token--------------------------------//

    public static final String Stripe_Token_ID = "token";
    public static final String PAYMENT_AMOUNT = "amount";
    public static final String PAYMENT_STATUS = "status";


    //---------------------Constants for OnBoardActivity --------------------------------//


    public static final String TITLE_OnBoard = "title";
    public static final String DESCRIPTION_OnBoard = "description";
    public static final String PHOTO_OnBoard = "photo";

    //---------------------Constants for Card Details --------------------------------//


    public static final String CARD_HOLDER = "Card_Name";
    public static final String Card_Number = "Card_Number";
    public static final String CARD_Expiry = "Expiry_Date";
    public static final String CARD_CVV = "CVV";
    public static final String CardImg_Token = "CardImg_Token";
    public static final String Card_USER_id = "user_id";
    public static final String Card_id = "id";
    public static final String Card_type = "card_type";
    public static final String Delete_Card_id = "card_id";


//---------------------Constants for Card Details --------------------------------//

    public static final String REVIEW_ID = "id";
    public static final String REVIEW_USER_ID = "user_id";
    public static final String REVIEW_SALON_NAME = "salon_name";
    public static final String REVIEW_RAT = "rating";
    public static final String REVIEW_LABEL = "review";
    public static final String REVIEW_CREATE = "created";


    //---------------------Constants for Fav Salon --------------------------------//

    public static final String FAV_SALON_ID = "salon_id";
    public static final String FAV_SALON_IMG = "profile_image";
    public static final String FAV_SALON_NAME = "salon_name";
    public static final String FAV_SALON_ADDRESS = "address";
    public static final String FAV_SALON_LAT = "latitude";
    public static final String FAV_SALON_LONG = "longitude";
    public static final String FAV_SALON_CONTACT = "contact";
    public static final String FAV_SALON_RATING = "salon_rating";

    public static final String FAV_SALON_TOTAL_RATING = "total_no_of_rating_reviews";
    public static final String FAV_SALON_OVERALL_RATING = "overall_rating";
    public static final String FAV_SALON_RAT_1 = "rating1";
    public static final String FAV_SALON_RAT_2 = "rating2";
    public static final String FAV_SALON_RAT_3 = "rating3";
    public static final String FAV_SALON_RAT_4 = "rating4";
    public static final String FAV_SALON_RAT_5 = "rating5";


    //---------------------  Constants to Make salon Fav  --------------------------------//

    public static final String MAKE_FAV_SALON_USER_ID = "user_id";
    public static final String MAKE_FAV_SALON_ID = "salon_id";
    public static final String IS_SALON_FAV = "is_salon_fav";


    //---------------------  Constants for PRofile  --------------------------------//


    public static final String PROFILE_USER_ID = "user_id";
    public static final String PROFILE_ADD_NUMBER = "number";
    public static final String PROFILE_DELETE_NUMBER = "number";
    public static final String PROFILE_IMAGE = "image";
    public static final String PROFILE_PWD = "password";


    //---------------------  Constants for SMS  --------------------------------//

    public static final String SMS_TO = "to_number";
    public static final String SMS_MESSAGE = "message";


//---------------------Constants for Previously Visited Salon --------------------------------//

    public static final String PRE_SALON_ID = "id";
    public static final String PRE_SALON_IMG = "profile_image";
    public static final String PRE_SALON_NAME = "name";
    public static final String PRE_SALON_ADDRESS = "address";
    public static final String PRE_SALON_LAT = "latitude";
    public static final String PRE_SALON_LONG = "longitude";
    public static final String PRE_SALON_CONTACT = "contact";
    public static final String PRE_SALON_RATING = "salon_rating";

    public static final String PRE_SALON_TOTAL_RATING = "total_no_of_rating_reviews";
    public static final String PRE_SALON_OVERALL_RATING = "overall_rating";
    public static final String PRE_SALON_RAT_1 = "rating1";
    public static final String PRE_SALON_RAT_2 = "rating2";
    public static final String PRE_SALON_RAT_3 = "rating3";
    public static final String PRE_SALON_RAT_4 = "rating4";
    public static final String PRE_SALON_RAT_5 = "rating5";


//---------------------Constants for Available Stylists  --------------------------------//

    public static final String AVL_STYLIST_ARRAY = "stylists";
    public static final String AVL_TRT_SLOTS = "treatments";

    public static final String AVL_TODAY_SLOTS = "today_time_slots";
    public static final String AVL_TOMORROW_SLOTS = "tomorrow_time_slots";

    public static final String AVL_STL_ID = "id";
    public static final String AVL_SALON_ID = "salon_id";
    public static final String AVL_STL_TYPE = "stylist_type";
    public static final String AVL_STL_DESCRIPTION = "description";
    public static final String AVL_STL_TITLE = "title";
    public static final String AVL_STL_IMAGE = "image";
    public static final String AVL_STL_OFFER = "offer";
    public static final String AVL_STL_CREATED = "created";
    public static final String AVL_STL_IS_ACTIVE = "is_active";
    public static final String AVL_STL_EMAIL = "email";
    public static final String AVL_STL_PWD = "password";
    public static final String AVL_STL_CONTACT = "contact";
    public static final String AVL_STL_DP = "requested_dp";
    public static final String AVL_STL_RAN_NUM = "ran_num";


}
