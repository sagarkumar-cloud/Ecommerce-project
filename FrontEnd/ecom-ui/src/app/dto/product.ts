export class ProductDto {

    public constructor(
        public productId:number,
        public name: string,
        public description: string,
        public title:string,
        public unitPrice:number,
        public imageURL:string,
        public active:boolean,
        public unitInStocks:number
    ) {
    }
}
