# gs-cart-checkout-system
Grocery Store Checkout System Demo

## Overview :
This is a straightforward REST service designed to calculate the bill for a grocery store by applying discount coupons.

## About :
This repository contains a demo implementation of a grocery store shopping cart and checkout system by applying coupon discounts.

## Prerequisities :
- Java JDK 21 or higher
- Maven 3.9.9 or higher

## Assumptions :
- Only one coupon should be active for an item. If more than one identified the API will consider only the 1st active coupon.
- Item name should be consistent and should be used as it is loaded from the configuration json file.
- DB is not included and hence used Below json to load the item and coupons records
  - src/main/resources/items.json :
    - will load the list of items and each unit price. (add more elements to this json to make more items available)
      - Sample content :
	      ```
	      [
			  {
			    "name": "banana",
			    "price": 0.5
			  },
			  {
			    "name": "orange",
			    "price": 0.3
			  }
		  ]
	      ```
  - src/main/resources/coupons.json :
     - will load the list of coupons with details. (add more elements to this json to make more coupons available).
     - Only those coupons which has its 'enable' parameter as 'true' will be considered. This parameter can be used for disabling and enabling coupons.
       - Sample content :
	       ```
	       [
			  {
			    "name": "Buy 2 Get1 Free (Bananas)",
			    "itemKey": "banana",
			    "type": "BUY_GET_FREE",
			    "buyQty": 2,
			    "freeQty": 1,
			    "bundleQty": null,
			    "bundlePrice": null,
			    "enabled": true
			  }
		   ]
	       ```

## Common Command :
- Clone project to local system.
```
git clone https://github.com/satendra-singh-java/gs-cart-checkout-system.git
```
- Build project with maven.
```
cd gs-cart-checkout-system
mvn clean install
```
- Run service
```
mvn spring-boot:run
```

## Design of the service :
- Followed Architecture :
  - Layered Architecture.
- Used Design Patterns :
  - Singleton Design pattern.
  - Strategy Design Pattern.
  - MVC Design pattern.

## Endpoints :
### For JSON request and Response :
This API will take a json with parameter as 'cartInput', which is a string type and produces a json response.
- URL :
  ```
  localhost:8080/api/cart/compute
  ```
- Http Method :
  ```
  POST
  ```
- Request Body :
  ```
  {
	"cartInput": "3 Banana, 3 Orange, 1 Apple"
  }
  ```
- sample output
  ```
  {
  "discounts": [
    {
      "couponName": "3 Oranges for 0.75",
      "discountAmount": 0.15
    },
    {
      "couponName": "Buy 2 Get1 Free (Bananas)",
      "discountAmount": 0.5
    }
  ],
  "finalAmount": 2.35,
  "preDiscountItems": [
    {
      "name": "Orange",
      "quantity": 3,
      "unitPrice": 0.3,
      "lineTotal": 0.9
    },
    {
      "name": "Banana",
      "quantity": 3,
      "unitPrice": 0.5,
      "lineTotal": 1.5
    },
    {
      "name": "Apple",
      "quantity": 1,
      "unitPrice": 0.6,
      "lineTotal": 0.6
    }
  ],
  "subtotal": 3,
  "totalDiscount": 0.65
  }
  ```
Important parameters in response jsons are : 
- totalDiscount : this is the total discount applied on the cart.
- subtotal : this is the total amount before discount.
- finalAmount : this is the amount finally paid (this price is after discount)
### Access service Via UI (Thymeleaf used to return View Pages)
- To submit request (cart elements)
  - URL :
    ```
    http://localhost:8080/api/cart
    ```
  - Above Url will return below HTML Page :
    
    <img width="460" height="155" alt="image" src="https://github.com/user-attachments/assets/597c1eda-cb30-4885-b67f-2cbdaf0f98f5" />

  - Input below value to the input box and hit Compute Button :
    ```
    3 Banana,4 Orange,1 Apple
    ```
    <img width="450" height="173" alt="image" src="https://github.com/user-attachments/assets/d72aaae4-3be1-468e-bdd2-dccbe586a346" />

  - Following above will return Bill response as Below :
    
    <img width="242" height="314" alt="image" src="https://github.com/user-attachments/assets/51d1e22b-68b6-4cd1-b9a2-3d51365dfd06" />
    
    ```
    -------------Bill Pre Discount----------
    Item	Quantity	Price	Total
    Orange	4	0.3	1.2
    Banana	3	0.5	1.5
    Apple	1	0.6	0.6
    -----------------------------------------------------
    Sub Total	3.3
    ---------------Discounts----------------------------
    Coupon	Discount
    3 Oranges for 0.75	0.15
    Buy 2 Get1 Free (Bananas)	0.5
    -----------------------------------------------------
    Total Discount :	0.65
    Total :	2.65
    ----------------------------------------------------
    ```
    

    
 




