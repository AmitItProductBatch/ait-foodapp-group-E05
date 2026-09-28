package com.ait.app.requestbody;

public class OrderTotalResponseDto {

	  	private Double itemSubtotal;
	    private Double tax;
	    private Double deliveryFee;
	    private Double discount;
	    private Double total;
	    
		public Double getItemSubtotal() {
			return itemSubtotal;
		}
		public void setItemSubtotal(Double itemSubtotal) {
			this.itemSubtotal = itemSubtotal;
		}
		public Double getTax() {
			return tax;
		}
		public void setTax(Double tax) {
			this.tax = tax;
		}
		public Double getDeliveryFee() {
			return deliveryFee;
		}
		public void setDeliveryFee(Double deliveryFee) {
			this.deliveryFee = deliveryFee;
		}
		public Double getDiscount() {
			return discount;
		}
		public void setDiscount(Double discount) {
			this.discount = discount;
		}
		public Double getTotal() {
			return total;
		}
		public void setTotal(Double total) {
			this.total = total;
		}
		public void setMenuItemId(int menuItemId) {
			// TODO Auto-generated method stub
			
		}
	    
	    
}
