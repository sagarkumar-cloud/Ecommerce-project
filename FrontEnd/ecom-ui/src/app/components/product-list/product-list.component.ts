import { Component, OnInit, inject } from '@angular/core';
import { ProductDto } from '../../dto/product';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Cartitem } from '../../dto/cartitem';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']  // fixed here
})
export class ProductListComponent implements OnInit {

  product: ProductDto[] = [];
  currentCategoryId = 1;
  searchMode: boolean = false;
  service = inject(ProductService);
  router = inject(ActivatedRoute)
  cartService=inject(CartService)

  ngOnInit(): void {
    this.router.paramMap.subscribe(() => {
      this.getAllProducts();
    })
  }

  getAllProducts() {
    this.searchMode = this.router.snapshot.paramMap.has("keyword");
    if (this.searchMode) {
      this.handleSearchProducts();
    } else {
      this.handleListProducts();
    }
  }

  handleListProducts() {
    var hasCategoryId = this.router.snapshot.paramMap.has("id");
    if (hasCategoryId) {
      this.currentCategoryId = +this.router.snapshot.paramMap.get('id')!;
    } else {
      this.currentCategoryId = 1;
    }
    this.service.getProductByCategoryId(this.currentCategoryId).subscribe(res => {
      this.product = res.data;
      console.log(this.product);
    });
  }

  handleSearchProducts() {
    const theKeyword: string = this.router.snapshot.paramMap.get('keyword')!;
    this.service.searchProducts(theKeyword).subscribe(res => {
      this.product = res.data;
    })
  }

  addToCart(theProduct: ProductDto) {
    const theCartItem = new Cartitem(theProduct);
    this.cartService.addToCart(theCartItem);
  }
}
