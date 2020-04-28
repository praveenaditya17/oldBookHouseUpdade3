import { Component, OnInit } from '@angular/core';
import { JavaServiceService } from '../java-service.service';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { BookDeliverAddressComponent } from '../book-deliver-address/book-deliver-address.component';
import { AddAddressService } from '../share/add-address.service';

@Component({
  selector: 'app-buy-book',
  templateUrl: './buy-book.component.html',
  styleUrls: ['./buy-book.component.css']
})
export class BuyBookComponent implements OnInit {
  bookDetail:any;
  constructor(private javaServiceObj:JavaServiceService,public addreqService:AddAddressService,
    public dialog:MatDialog) { }

  ngOnInit() {
    this.javaServiceObj.getBookById(this.javaServiceObj.bookId).subscribe((book) => {
      this.bookDetail=book;
      // console.log(this.bookDetail);
  });
  }
  addToCart(bookId:number){
    this.javaServiceObj.addSellOrderRequest(bookId);
  }
  
  buyNow(bookId:number){
    // console.log(bookId);
    this.javaServiceObj.bookId=bookId;
    this.javaServiceObj.checkCart=false;
    this.addreqService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "50%";
    this.dialog.open(BookDeliverAddressComponent,dialogConfig);
  }

}
