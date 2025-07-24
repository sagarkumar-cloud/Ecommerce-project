import { Injectable } from '@angular/core';
import { Cartitem } from '../dto/cartitem';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItems: Cartitem[] = []
  totalPrice: Subject<number> = new Subject<number>();
  totalQuantity: Subject<number> = new Subject<number>();

  addToCart(theCartItem: Cartitem) {
    let alredyExistInTheCart: boolean = false;
    let existingCartItem!: Cartitem;

    if (this.cartItems.length > 0) {

      for (var tempCartItem of this.cartItems) {
        if (tempCartItem.productId === theCartItem.productId) {
          existingCartItem = tempCartItem;
          alredyExistInTheCart = true;
          break;
        }
      }
    }
    if (alredyExistInTheCart) {
      existingCartItem.quantity++;
    } else {
      this.cartItems.push(theCartItem);
    }
    this.computeCartTotals();
  }

  computeCartTotals() {
    var totalPriceValue = 0;
    var totalQuantityValue = 0;
    for (let currentCartItem of this.cartItems) {
      totalPriceValue += currentCartItem.quantity * currentCartItem.unitPrice;
      totalQuantityValue += currentCartItem.quantity;
    }
    // publish new values for all subscribers
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);
  }

  decrementQuantity(theCartItem: Cartitem) {
    theCartItem.quantity--;
    if (theCartItem.quantity == 0) {
      this.remove(theCartItem);
    } else {
      this.computeCartTotals();
    }
  }

  remove(theCartItem: Cartitem) {
    // get index of item in the array
    const itemIndex = this.cartItems.findIndex(tempCartItem => tempCartItem.productId == theCartItem.productId);
    if (itemIndex > -1) {
      this.cartItems.splice(itemIndex, 1);
      this.computeCartTotals();
    }
  }
}
