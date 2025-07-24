import { ProductDto } from "./product";

export class Cartitem {
    productId: number;
    name: string;
    imageUrl: string;
    unitPrice: number;
    quantity: number;

    constructor(product: ProductDto){
        this.productId = product.productId;
         this.name = product.name;
        this.imageUrl = product.imageURL;
        this.unitPrice = product.unitPrice;
        this.quantity = 1;
    }
}
