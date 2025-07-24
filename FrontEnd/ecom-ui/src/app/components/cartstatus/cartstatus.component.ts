import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cartstatus',
  standalone:true,
  imports: [RouterLink],
  templateUrl: './cartstatus.component.html',
  styleUrl: './cartstatus.component.css'
})
export class CartstatusComponent implements OnInit{

  totalPrice: number = 0;
  totalQuantity: number = 0;

  constructor(private cartService:CartService) { }

  ngOnInit(): void {
    this.updateCartStatus();
  }

  updateCartStatus() {
    this.cartService.totalPrice.subscribe(
      data => this.totalPrice = data
    );
    this.cartService.totalQuantity.subscribe(
      data => this.totalQuantity = data
    );
  }
}

