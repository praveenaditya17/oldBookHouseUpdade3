import { Component, OnInit } from '@angular/core';
import { JavaServiceService } from '../java-service.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import { NotificationService } from '../share/notification.service';

@Component({
  selector: 'app-show-book',
  templateUrl: './show-book.component.html',
  styleUrls: ['./show-book.component.css']
})
export class ShowBookComponent implements OnInit {
  bookList:any;
  constructor(public javaService:JavaServiceService,public router:Router,
    private hasLogin:AuthenticationService,private notificationService:NotificationService) { 
    this.bookList=this.javaService.bookList;
    console.log(this.bookList)
  }

  ngOnInit() {
  }
  viewBook(sellOrderRequestId:number){
    this.javaService.bookId=sellOrderRequestId;
    this.router.navigate(['/buybook']);
  }
  addToCart(bookId:number){
    if(this.hasLogin.isUserLoggedIn()){
      this.javaService.addSellOrderRequest(bookId);
      this.notificationService.success("book added sucessfully");
    }else{
      this.notificationService.warn("please Login first.");
    }
    
  }

}
