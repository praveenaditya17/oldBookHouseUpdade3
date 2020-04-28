import { Component, OnInit } from '@angular/core';
import { JavaServiceService } from '../java-service.service';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { BookDeliverAddressComponent } from '../book-deliver-address/book-deliver-address.component';
import { AddAddressService } from '../share/add-address.service';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  bookList:any;
  constructor( private javaServiceObj:JavaServiceService,public addreqService:AddAddressService,
    public dialog:MatDialog,private hasLogin:AuthenticationService) { }

  ngOnInit() {
    if(this.hasLogin.isUserLoggedIn()){
      this.javaServiceObj.getBuyBook().subscribe(
        book=>{
          this.bookList=book;
          console.log(this.bookList);
        }
      );
     }
  }
  deleteBookRequest(requestId:number){
    // console.log(requestId);
    this.javaServiceObj.delteBookRequest(requestId).subscribe(
      data=>{
        this.javaServiceObj.getBookNotification();
      }
    )
  }
  buyNow(){
    this.javaServiceObj.checkCart=true;
    this.addreqService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "50%";
    this.dialog.open(BookDeliverAddressComponent,dialogConfig);
  }

}
