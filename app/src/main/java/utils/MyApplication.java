package utils;

/**
 * Created by yadi on 22/05/16.
 */
// Add this to the header of your file:

import android.app.Application;
import android.provider.Settings.Secure;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.HashMap;

public class MyApplication extends Application {

    //Updated your class body:
    boolean readonly;
    boolean run;
    boolean fblogin;

    //***********************  Login API variables and getter and setter  *************************

    String login_email_id;

    public String getLogin_email_id() {
        return login_email_id;
    }

    public void setLogin_email_id(String login_email_id) {
        this.login_email_id = login_email_id;
    }

    public String getLogin_user_name() {
        return login_user_name;
    }

    public void setLogin_user_name(String login_user_name) {
        this.login_user_name = login_user_name;
    }

    String login_user_name;

    public boolean isFblogin() {
        return fblogin;
    }

    public void setFblogin(boolean fblogin) {
        this.fblogin = fblogin;
    }


    //***********************  Booking API variables and getter and setter  *************************

    public int getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(int booking_status) {
        this.booking_status = booking_status;
    }

    int booking_status;

    //***********************  Treatment API variables and getter and setter  *************************


    ArrayList<String> selected_treatment_id= new ArrayList<>();
    ArrayList<String> selected_treatment_price= new ArrayList<>();
    ArrayList<String> selected_treatment_name= new ArrayList<>();

    public ArrayList<String> getSelected_treatment_name() {
        return selected_treatment_name;
    }

    public void setSelected_treatment_name(ArrayList<String> selected_treatment_name) {
        this.selected_treatment_name = selected_treatment_name;
    }

    public ArrayList<String> getSpecial_selected_price() {
        return special_selected_price;
    }

    public void setSpecial_selected_price(ArrayList<String> special_selected_price) {
        this.special_selected_price = special_selected_price;
    }

    ArrayList<String> special_selected_price;
    ArrayList<String> special_selected_id;
    ArrayList<String> special_selected_name;

    public ArrayList<String> getSpecial_selected_name() {
        return special_selected_name;
    }

    public void setSpecial_selected_name(ArrayList<String> special_selected_name) {
        this.special_selected_name = special_selected_name;
    }

    public ArrayList<String> getSpecial_selected_id() {
        return special_selected_id;
    }

    public void setSpecial_selected_id(ArrayList<String> special_treatment_id) {
        this.special_selected_id = special_treatment_id;
    }


    public ArrayList<String> getSelected_treatment_id() {
        return selected_treatment_id;
    }

    public void setSelected_treatment_id(ArrayList<String> selected_treatment_id) {
        this.selected_treatment_id = selected_treatment_id;
    }

    public ArrayList<String> getSelected_treatment_price() {
        return selected_treatment_price;
    }

    public void setSelected_treatment_price(ArrayList<String> selected_treatment_price) {
        this.selected_treatment_price = selected_treatment_price;
    }

    public boolean isTreatmentSelected() {
        return isTreatmentSelected;
    }

    public void setTreatmentSelected(boolean treatmentSelected) {
        isTreatmentSelected = treatmentSelected;
    }

    public boolean isTreatmentSelected;

    public boolean isSpecialTreatmentSelected() {
        return isSpecialTreatmentSelected;
    }

    public void setSpecialTreatmentSelected(boolean specialTreatmentSelected) {
        isSpecialTreatmentSelected = specialTreatmentSelected;
    }

    public boolean isSpecialTreatmentSelected;



    //***********************  Profile API variables and getter and setter  *************************

    ArrayList<String> user_contact_no;

    public String getDelete_number() {
        return delete_number;
    }

    public void setDelete_number(String delete_number) {
        this.delete_number = delete_number;
    }

    String delete_number;

    public String getAdd_number() {
        return add_number;
    }

    public void setAdd_number(String add_number) {
        this.add_number = add_number;
    }

    String add_number;

    public ArrayList<String> getUser_contact_no() {
        return user_contact_no;
    }

    public void setUser_contact_no(ArrayList<String> user_contact_no) {
        this.user_contact_no = user_contact_no;
    }


    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //***********************  Filter API variables and getter and setter  *************************

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    boolean filter;

    public boolean isFavorite_filter() {
        return favorite_filter;
    }

    public void setFavorite_filter(boolean favorite_filter) {
        this.favorite_filter = favorite_filter;
    }

    public boolean isPrev_visit_filter() {
        return prev_visit_filter;
    }

    public void setPrev_visit_filter(boolean prev_visit_filter) {
        this.prev_visit_filter = prev_visit_filter;
    }

    boolean favorite_filter;
    boolean prev_visit_filter;

    public String devicetoken;
    int item_selected;

    public int getSelected_salon() {
        return selected_salon;
    }

    public void setSelected_salon(int selected_salon) {
        this.selected_salon = selected_salon;
    }

    int selected_salon;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String UserID;
    public String SalonID;
    public String SortBy;
    public String distance;

    public String lat;
    public String lang;
    public String place_name;

    public String filterlat;

    public void setFilterLat(String lat) {
        this.filterlat = lat;
    }

    public String getFilterlat() {
        return filterlat;
    }

    public String filterlong;

    public void setFilterlong(String lang) {
        this.filterlong = lang;
    }

    public String getFilterlong() {
        return filterlong;
    }


    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getFilterRating() {
        return filterRating;
    }

    public void setFilterRating(String filterRating) {
        this.filterRating = filterRating;
    }

    public String filterRating;

    public String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String time;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSortBy() {
        return SortBy;
    }

    public void setSortBy(String sortBy) {
        SortBy = sortBy;
    }

    public String getHighLow() {
        return highLow;
    }

    public void setHighLow(String highLow) {
        this.highLow = highLow;
    }

    public String highLow;

    public String price_from;
    public String price_to;

    public String getPrice_from() {
        return price_from;
    }

    public void setPrice_from(String price_from) {
        this.price_from = price_from;
    }

    public String getPrice_to() {
        return price_to;
    }

    public void setPrice_to(String price_to) {
        this.price_to = price_to;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDistance_from() {
        return distance_from;
    }

    public void setDistance_from(String distance_from) {
        this.distance_from = distance_from;
    }

    public String getDistance_to() {
        return distance_to;
    }

    public void setDistance_to(String distance_to) {
        this.distance_to = distance_to;
    }

    public String available;
    public String distance_from;
    public String distance_to;



    // *************************** Filter variables ends here  ***************************************

    public ArrayList<String> getAl_treatment_price() {
        return al_treatment_price;
    }

    public void setAl_treatment_price(ArrayList<String> al_treatment_price) {
        this.al_treatment_price = al_treatment_price;
    }

    public ArrayList<String> al_treatment_price;


    public String StylistID;
    public String TreatmentID;
    public String bookingTime;
    public String stylist_name;

    public String getStylist_name() {
        return stylist_name;
    }

    public void setStylist_name(String stylist_name) {
        this.stylist_name = stylist_name;
    }

    public String getStylistID() {
        return StylistID;
    }

    public void setStylistID(String stylistID) {
        StylistID = stylistID;
    }

    public String getTreatmentID() {
        return TreatmentID;
    }

    public void setTreatmentID(String treatmentID) {
        TreatmentID = treatmentID;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }



    public String getTreatmentName() {
        return TreatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        TreatmentName = treatmentName;
    }

    public String TreatmentName;

    public ArrayList<String> getAl_treatment_id() {
        return al_treatment_id;
    }

    public void setAl_treatment_id(ArrayList<String> al_treatment_id) {
        this.al_treatment_id = al_treatment_id;
    }

    public ArrayList<String> al_treatment_id = new ArrayList<>();

    public ArrayList<String> al_treatment_name = new ArrayList<>();

    public ArrayList<String> getAl_treatment_name() {
        return al_treatment_name;
    }

    public void setAl_treatment_name(ArrayList<String> al_treatment_name) {
        this.al_treatment_name = al_treatment_name;
    }

    public ArrayList<String> getTreamentSelectedListing() {
        return treamentSelectedListing;
    }

    public void setTreamentSelectedListing(ArrayList<String> treamentSelectedListing) {
        this.treamentSelectedListing = treamentSelectedListing;
    }

    public int book_total_time;

    public int getBook_total_time() {
        return book_total_time;
    }

    public void setBook_total_time(int book_total_time) {
        this.book_total_time = book_total_time;
    }

    public ArrayList<String> treamentSelectedListing;

    public ArrayList<String> TodayListing;

    public ArrayList<String> getTomorrowListing() {
        return TomorrowListing;
    }

    public void setTomorrowListing(ArrayList<String> tomorrowListing) {
        TomorrowListing = tomorrowListing;
    }

    public ArrayList<String> getTodayListing() {
        return TodayListing;
    }

    public void setTodayListing(ArrayList<String> todayListing) {
        TodayListing = todayListing;
    }

    public ArrayList<String> TomorrowListing;


    public int getItem_selected() {
        return item_selected;
    }

    public void setItem_selected(int item_selected) {
        this.item_selected = item_selected;
    }

    public String getSelectedSalonID() {
        return selectedSalonID;
    }

    public void setSelectedSalonID(String selectedSalonID) {
        this.selectedSalonID = selectedSalonID;
    }

    public String selectedSalonID;

    public String getTotal_treatment_cost() {
        return total_treatment_cost;
    }

    public void setTotal_treatment_cost(String total_treatment_cost) {
        this.total_treatment_cost = total_treatment_cost;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String token_id;

    public String total_treatment_cost;

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int paymentStatus;

    public ArrayList<HashMap<String, String>> getSalonDetailListing() {
        return SalonDetailListing;
    }

    public void setSalonDetailListing(ArrayList<HashMap<String, String>> salonDetailListing) {
        SalonDetailListing = salonDetailListing;
    }

    public ArrayList<HashMap<String, String>> SalonListing;

    public ArrayList<HashMap<String, String>> getStylistListing() {
        return StylistListing;
    }

    public void setStylistListing(ArrayList<HashMap<String, String>> stylistListing) {
        StylistListing = stylistListing;
    }

    public ArrayList<HashMap<String, String>> ReviewListing;
    public ArrayList<HashMap<String, String>> LoginListing;
    public ArrayList<HashMap<String, String>> SignupListing;
    public ArrayList<HashMap<String, String>> SalonDetailListing;
    public ArrayList<HashMap<String, String>> StylistListing;
    public ArrayList<HashMap<String, String>> BookedListing;

    public HashMap<String, String> bookingDetails;

    public HashMap<String, String> getProfileDetails() {
        return ProfileDetails;
    }

    public void setProfileDetails(HashMap<String, String> profileDetails) {
        ProfileDetails = profileDetails;
    }

    public HashMap<String, String> ProfileDetails;

    public ArrayList<HashMap<String, String>> getBookedListing() {
        return BookedListing;
    }

    public void setBookedListing(ArrayList<HashMap<String, String>> bookedListing) {
        BookedListing = bookedListing;
    }

    public HashMap<String, String> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(HashMap<String, String> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public String booked_treatments;

    public String getBooked_treatments() {
        return booked_treatments;
    }

    public void setBooked_treatments(String booked_treatments) {
        this.booked_treatments = booked_treatments;
    }

    public ArrayList<HashMap<String, String>> NormalTreatmentListing;
    public ArrayList<HashMap<String, String>> OfferTreatmentListing;

    public ArrayList<HashMap<String, String>> getNormalTreatmentListing() {
        return NormalTreatmentListing;
    }

    public void setNormalTreatmentListing(ArrayList<HashMap<String, String>> normalTreatmentListing) {
        NormalTreatmentListing = normalTreatmentListing;
    }

    public ArrayList<HashMap<String, String>> getOfferTreatmentListing() {
        return OfferTreatmentListing;
    }

    public void setOfferTreatmentListing(ArrayList<HashMap<String, String>> offerTreatmentListing) {
        OfferTreatmentListing = offerTreatmentListing;
    }

    public ArrayList<String> SalonImageListing;

    public ArrayList<String> getSalonImageListing() {
        return SalonImageListing;
    }

    public void setSalonImageListing(ArrayList<String> salonImageListing) {
        SalonImageListing = salonImageListing;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public ArrayList<HashMap<String, String>> getLoginListing() {
        return LoginListing;
    }

    public void setLoginListing(ArrayList<HashMap<String, String>> loginListing) {
        LoginListing = loginListing;
    }

    public void setSalonListing(ArrayList<HashMap<String, String>> salonListing) {
        SalonListing = salonListing;
    }

    public ArrayList<HashMap<String, String>> getSalonListing() {
        return SalonListing;
    }

    public ArrayList<HashMap<String, String>> getReviewListing() {
        return ReviewListing;
    }

    public void setReviewListing(ArrayList<HashMap<String, String>> reviewListing) {
        ReviewListing = reviewListing;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public ArrayList<HashMap<String, String>> getSignupListing() {
        return SignupListing;
    }

    public void setSignupListing(ArrayList<HashMap<String, String>> signupListing) {
        SignupListing = signupListing;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public ArrayList<HashMap<String, String>> getTreatmentListing() {
        return TreatmentListing;
    }

    public void setTreatmentListing(ArrayList<HashMap<String, String>> treatmentListing) {
        TreatmentListing = treatmentListing;
    }

    public ArrayList<HashMap<String, String>> TreatmentListing;


    public ArrayList<HashMap<String, String>> getOnBoardListing() {
        return onBoardListing;
    }

    public void setOnBoardListing(ArrayList<HashMap<String, String>> onBoardListing) {
        this.onBoardListing = onBoardListing;
    }

    public ArrayList<HashMap<String, String>> onBoardListing;


    //***********************  Search API variables and getter and setter  *************************


    public String src_KeyWord;
    public String src_lat;
    public String src_long;
    public String src_date_time;
    public String src_treatment_id;
    public String src_salon_id;
    public String src_salon_name;

    public String getSrc_salon_name() {
        return src_salon_name;
    }

    public void setSrc_salon_name(String src_salon_name) {
        this.src_salon_name = src_salon_name;
    }

    //   public ArrayList<String> al_search_items;
    public ArrayList<String> al_search_salon_id = new ArrayList<>();

    public HashMap<String, String> getAl_search_items() {
        return al_search_items;
    }

    public void setAl_search_items(HashMap<String, String> al_search_items) {
        this.al_search_items = al_search_items;
    }

    public HashMap<String,String> al_search_items = new HashMap<>();

    public ArrayList<String> getAl_search_salon() {
        return al_search_salon;
    }

    public void setAl_search_salon(ArrayList<String> al_search_salon) {
        this.al_search_salon = al_search_salon;
    }

    public ArrayList<String> al_search_salon = new ArrayList<>();


    public String getSrc_KeyWord() {
        return src_KeyWord;
    }

    public void setSrc_KeyWord(String src_KeyWord) {
        this.src_KeyWord = src_KeyWord;
    }

    public String getSrc_lat() {
        return src_lat;
    }

    public void setSrc_lat(String src_lat) {
        this.src_lat = src_lat;
    }

    public String getSrc_long() {
        return src_long;
    }

    public void setSrc_long(String src_long) {
        this.src_long = src_long;
    }

    public String getSrc_date_time() {
        return src_date_time;
    }

    public void setSrc_date_time(String src_date_time) {
        this.src_date_time = src_date_time;
    }

    public String getSrc_salon_id() {
        return src_salon_id;
    }

    public void setSrc_salon_id(String src_salon_id) {
        this.src_salon_id = src_salon_id;
    }

    public String getSrc_treatment_id() {
        return src_treatment_id;
    }

    public void setSrc_treatment_id(String src_treatment_id) {
        this.src_treatment_id = src_treatment_id;
    }


    //***********************  ADD CARD API variables and getter and setter  *************************

    public String card_holder;
    public String card_no;
    public String card_expiry;
    public String card_cvv;
    public String card_img_token;
    public String card_id;
    public String card_type;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public ArrayList<HashMap<String, String>> getAl_card_details() {
        return al_card_details;
    }

    public void setAl_card_details(ArrayList<HashMap<String, String>> al_card_details) {
        this.al_card_details = al_card_details;
    }

    public ArrayList<HashMap<String,String>> al_card_details = new ArrayList<>();

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String user_id;


    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_holder() {
        return card_holder;
    }

    public void setCard_holder(String card_holder) {
        this.card_holder = card_holder;
    }

    public String getCard_expiry() {
        return card_expiry;
    }

    public void setCard_expiry(String card_expiry) {
        this.card_expiry = card_expiry;
    }

    public String getCard_cvv() {
        return card_cvv;
    }

    public void setCard_cvv(String card_cvv) {
        this.card_cvv = card_cvv;
    }

    public String getCard_img_token() {
        return card_img_token;
    }

    public void setCard_img_token(String card_img_token) {
        this.card_img_token = card_img_token;
    }


    // ***********************  Given Reviews variables  ********************************************

    public String review_salon_name;
    public String review_id;
    public String review_rating;
    public String review_label;
    public String review_created;
    public String review_user_id;

    public ArrayList<HashMap<String,String>> al_given_review = new ArrayList<>();


    public String getReview_salon_name() {
        return review_salon_name;
    }

    public void setReview_salon_name(String review_salon_name) {
        this.review_salon_name = review_salon_name;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(String review_rating) {
        this.review_rating = review_rating;
    }

    public String getReview_label() {
        return review_label;
    }

    public void setReview_label(String review_label) {
        this.review_label = review_label;
    }

    public String getReview_created() {
        return review_created;
    }

    public void setReview_created(String review_created) {
        this.review_created = review_created;
    }

    public String getReview_user_id() {
        return review_user_id;
    }

    public void setReview_user_id(String review_user_id) {
        this.review_user_id = review_user_id;
    }

    public ArrayList<HashMap<String, String>> getAl_given_review() {
        return al_given_review;
    }

    public void setAl_given_review(ArrayList<HashMap<String, String>> al_given_review) {
        this.al_given_review = al_given_review;
    }




    // ***********************  Fav Salon variables  ********************************************

    public String fav_salon_id;

    public int getIs_salon_fav() {
        return is_salon_fav;
    }

    public void setIs_salon_fav(int is_salon_fav) {
        this.is_salon_fav = is_salon_fav;
    }

    public int is_salon_fav;


    public ArrayList<HashMap<String, String>> getAl_fav_salon() {
        return al_fav_salon;
    }

    public void setAl_fav_salon(ArrayList<HashMap<String, String>> al_fav_salon) {
        this.al_fav_salon = al_fav_salon;
    }

    public ArrayList<HashMap<String,String>> al_fav_salon = new ArrayList<>();



    // *********************** Salon Card List  ********************************************


    public ArrayList<HashMap<String, String>> getAl_salon_card() {
        return al_salon_card;
    }

    public void setAl_salon_card(ArrayList<HashMap<String, String>> al_salon_card) {
        this.al_salon_card = al_salon_card;
    }

    public ArrayList<HashMap<String,String>> al_salon_card = new ArrayList<>();


    // *********************** Salon Card List  ********************************************

    public String getMake_salon_fav_id() {
        return make_salon_fav_id;
    }

    public void setMake_salon_fav_id(String make_salon_fav_id) {
        this.make_salon_fav_id = make_salon_fav_id;
    }

    public String make_salon_fav_id;


    // ********************** Previously Visited Variables  *********************************


    public ArrayList<HashMap<String, String>> getAl_prev_visit() {
        return al_prev_visit;
    }

    public void setAl_prev_visit(ArrayList<HashMap<String, String>> al_prev_visit) {
        this.al_prev_visit = al_prev_visit;
    }

    public ArrayList<HashMap<String,String>> al_prev_visit = new ArrayList<>();


    // ********************** Product in cart  *********************************

    private ArrayList<Album> al_slctd_product = new ArrayList<>();

    public ArrayList<Album> getAl_slctd_product() {
        return al_slctd_product;
    }

    public void setAl_slctd_product(ArrayList<Album> al_slctd_product) {
        this.al_slctd_product = al_slctd_product;
    }



    private String product_total_cost;

    public String getProduct_total_cost() {
        return product_total_cost;
    }

    public void setProduct_total_cost(String product_total_cost) {
        this.product_total_cost = product_total_cost;
    }

    //******************************************************************************

    @Override
    public void onCreate() {

        super.onCreate();

        // Initialize the SDK before executing any other operations,
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
     //   FontsOverride.setDefaultFont(this, "SERIF", "fonts/sfthin.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/sanfrancisco.ttf");

        devicetoken = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        //FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/sanfrancisco.ttf");

        readonly = false;
        run = true;
    }

}