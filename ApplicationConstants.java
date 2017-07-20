package utils;

public interface ApplicationConstants {

	//Local DB

	public static String DATABASE_NAME="SARITHA";
	public static int DATABASE_VERSION=1;
	public static String TABLE_NAME="CART_SARITHA";
	public static String KEY_ID="KEY_ID";
	public static String KEY_couponname="KEY_couponname";
	public static String KEY_couponimage="KEY_couponimage";
	public static String KEY_couponcost="KEY_couponcost";
	public static String KEY_coupondiscount="KEY_coupondiscount";
	public static String KEY_couponquantity="KEY_couponquantity";
	public static String KEY_coupondescription="KEY_coupondescription";
	public static String KEY_couponid="KEY_couponid";
	public static String KEY_behaviour="KEY_behaviour";
	public static String KEY_availableCount="KEY_availableCount";



	public static String couponname="couponname";
	public static String couponimagepath="couponimagepath";
	public static String couponcost="couponcost";
	public static String coupondiscount="coupondiscount";
	public static String discounttype="discounttype";
	public static String expirytime="expirytime";
	public static String availablecouponquantity="availablecouponquantity";
	public static String coupondescription="coupondescription";
	public static String rating="rating";
	public static String review_basedon="review_basedon";


	public static String hospitalname="hospitalname";
	public static String hospitaldiscount="hospitaldiscount";
	public static String hospitallocation="hospitallocation";
	public static String hospitalimage="hospitalimage";
	public static String servicecost="servicecost";
	public static String servicediscount="servicediscount";

	// Pet Deal Application constants
	public static String detailpage_from="detailpage_from";
	public static String hospitallistfrom="hospitallistfrom";
	public static String mycartfrom="mycartfrom";
	public static String petshop_from="petshop_from";
	public static String favouritecoupons_from="favouritecoupons_from";
	public static String wishlist_from="wishlist_from";
	public static String orderslist_from="orderslist_from";
	public static String home_from="home_from";
	public static String settings_from="settings_from";
	public static String purchasecoupons_from="purchasecoupons_from";
	public static String events_from="events_from";
	public static String coupons_from="coupons_from";
	public static String checkout_from="checkout_from";


	public static String detailhospitalpage="detailhospitalpage";
	public static String completeredeemfrom="completeredeemfrom";

	public static String dealstitle="dealstitle";
	public static String Category="Category";
	public static String Category_wishlist="Category_wishlist";
	public static String Couponid="Couponid";
	public static String Productid="Productid";

	public static String home_type="home_type";

	public static String behaviour="behaviour";
	public static String total_bill="total_bill";
	public static String orderid="orderid";
	public static String orderitemid="orderitemid";
	public static String clinicid="clinicid";


	public static String Menufrom="menufrom";
	public static String loginstatus="skip";
	public static String favstatus="favstatus";
	public static String paymentstatus="paymentstatus";




	//Registration fields or Billing Address

	public static String email="email";
	public static String pwd="pwd";
	public static String fname="fname";
	public static String lname="lname";
	public static String phno="phno";
	public static String gender="gender";

	//Shipping_Address fields

	public static String shipping_email="shipping_email";
	public static String shipping_name="shipping_name";
	public static String shipping_phno="shipping_phno";
	public static String shipping_address="shipping_address";
	public static String shipping_city="shipping_city";
	public static String shipping_state="shipping_state";
	public static String shipping_postcode="shipping_postcode";

	public static String itemcode="code";
	public static String coupon_type="type";

	public static String BASE_URL="http://petdeal.logicprog.com/";
	public static String REGISTER_URL=BASE_URL+"mobileapi/registration.php";
	public static String LOGIN_URL=BASE_URL+"mobileapi/login.php";
	public static String FORGOTPWD_URL=BASE_URL+"mobileapi/forgotpassword.php";
	public static String Mobileverification_URL=BASE_URL+"mobileapi/checkotp.php";
	public static String ResendOTP_URL=BASE_URL+"mobileapi/resendotp.php";
	public static String GetbillingAddress_URL=BASE_URL+"mobileapi/getuserdata.php";
	public static String Topcoupons_URL=BASE_URL+"mobileapi/getpopular_coupons.php";
	public static String Category_URL=BASE_URL+"mobileapi/getcategory_coupons.php";
	public static String ProductCategory_URL=BASE_URL+"mobileapi/getcategory_products.php";
	public static String Wishlist_URL=BASE_URL+"mobileapi/addtowishlist.php";
	public static String getWishlist_URL=BASE_URL+"mobileapi/getwishlist.php";
	public static String getFavCoupons_URL=BASE_URL+"mobileapi/getfavcoupons.php";
	public static String getProducts_URL=BASE_URL+"mobileapi/getproducts.php";
	public static String getCoupons_URL=BASE_URL+"mobileapi/getcoupon.php";
	public static String getOrders_URL=BASE_URL+"mobileapi/getorders.php";
	public static String PlaceOrders_URL=BASE_URL+"mobileapi/createorder.php";
	public static String GetSubOrders_URL=BASE_URL+"mobileapi/getorderdata.php";
	public static String ProcessOrdersItems_URL=BASE_URL+"mobileapi/processorderitems.php";
	public static String ProductDetails_URL=BASE_URL+"mobileapi/singleproduct.php";
	public static String CouponDetails_URL=BASE_URL+"mobileapi/singlecoupon.php";
	public static String Unredeemed_URL=BASE_URL+"mobileapi/getunredeemedcoupons.php";
	public static String Redeemed_URL=BASE_URL+"mobileapi/getredeemedcoupons.php";
	public static String GetHospitals=BASE_URL+"mobileapi/selecthospital.php";
	public static String GetHospitalData=BASE_URL+"mobileapi/getclinicdata.php";
	public static String RedeemCoupon=BASE_URL+"mobileapi/redeem.php";
	public static String GetRedeemCouponData=BASE_URL+"mobileapi/redeem_complete.php";
	public static String Resend_Coupon=BASE_URL+"mobileapi/resend_coupon.php";
	public static String Deletewishlist=BASE_URL+"mobileapi/deletewishlist.php";
	public static String GetReview=BASE_URL+"mobileapi/getreview.php";
	public static String AddReview=BASE_URL+"mobileapi/addreview.php";
	public static String Editprofile=BASE_URL+"mobileapi/editprofile.php";
	public static String getshippingaddress=BASE_URL+"mobileapi/getaddress.php";
	public static String addshippingaddress=BASE_URL+"mobileapi/addaddress.php";
	public static String getselected_shippingaddress=BASE_URL+"mobileapi/get_selected_address.php";

	public static String Validate_Postcode=BASE_URL+"mobileapi/validatepostcode.php";
	public static String banners=BASE_URL+"mobileapi/get_mobilebanner.php";
	public static String search_coupons=BASE_URL+"mobileapi/search_coupon.php";
	public static String search_products=BASE_URL+"mobileapi/search_product.php";
	public static String Events=BASE_URL+"mobileapi/getevents.php";
	public static String PutEventStatus=BASE_URL+"mobileapi/event_request.php";
    public static String CancelEvent=BASE_URL+"mobileapi/event_cancelrequest.php";


	public static String HOME_SCREEN_UPDATE="http://www.logicprog.com/addon/moviemasala/homescreenrecentupdates.php";
	public static String RECENT_UPDATE_INDUSTRY="http://www.logicprog.com/addon/moviemasala/getrecentupdatesofindustry.php";


}