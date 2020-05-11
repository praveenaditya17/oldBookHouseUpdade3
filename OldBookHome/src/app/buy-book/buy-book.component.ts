import { Component, OnInit } from '@angular/core';
import { JavaServiceService } from '../java-service.service';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { BookDeliverAddressComponent } from '../book-deliver-address/book-deliver-address.component';
import { AddAddressService } from '../share/add-address.service';
import { AuthenticationService } from '../service/authentication.service';
import { NotificationService } from '../share/notification.service';

@Component({
  selector: 'app-buy-book',
  templateUrl: './buy-book.component.html',
  styleUrls: ['./buy-book.component.css']
})
export class BuyBookComponent implements OnInit {
  bookDetail:any;
  constructor(private javaServiceObj:JavaServiceService,public addreqService:AddAddressService,
    public dialog:MatDialog,private hasLogin:AuthenticationService,private notificationService:NotificationService) { }

  ngOnInit() {
    this.javaServiceObj.getBookById(this.javaServiceObj.bookId).subscribe((book) => {
      this.bookDetail=book;
    });
  }
  addToCart(bookId:number){
    if(this.hasLogin.isUserLoggedIn()){
      this.javaServiceObj.addSellOrderRequest(bookId);
    }else{
      this.notificationService.warn("please Login first.");
    }
  }
  
  buyNow(bookId:number,bookPrice:number){
    console.log(bookId);
    if(this.hasLogin.isUserLoggedIn()){
      this.javaServiceObj.bookId=bookId;
      this.javaServiceObj.totalPrice=bookPrice;
      this.javaServiceObj.checkCart=false;
      this.addreqService.initializeFormGroup();
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.width = "50%";
      this.dialog.open(BookDeliverAddressComponent,dialogConfig);
    }else{
      this.notificationService.warn("please Login first.");
    }
  }
}
