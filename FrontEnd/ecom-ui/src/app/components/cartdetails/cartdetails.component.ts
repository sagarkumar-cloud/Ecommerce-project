import { Component, OnInit } from '@angular/core';
import { Cartitem } from '../../dto/cartitem';
import { CartService } from '../../services/cart.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cartdetails',
  standalone:true,
  imports: [CommonModule,RouterLink],
  templateUrl: './cartdetails.component.html',
  styleUrl: './cartdetails.component.css'
})
export class CartdetailsComponent implements OnInit{

  cartItems: Cartitem[] = [];
  totalPrice: number = 0;
  totalQuantity: number = 0;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.getCartItems();
  }

  getCartItems() {
    this.cartItems = this.cartService.cartItems;

    // subscribe to total price
    this.cartService.totalPrice.subscribe(
      data => this.totalPrice = data
    );

    this.cartService.totalQuantity.subscribe(
      data => this.totalQuantity = data
    );
  }

  incrementQuantity(theCartItem: Cartitem) {
 this.cartService.addToCart(theCartItem);
}

decrementQuantity(theCartItem: Cartitem) {
 this.cartService.decrementQuantity(theCartItem);
}

remove(theCartItem: Cartitem) {
 this.cartService.remove(theCartItem);
}
}
