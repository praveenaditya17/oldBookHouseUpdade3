import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { JavaServiceService } from '../java-service.service';

@Component({
  selector: 'app-buy-history',
  templateUrl: './buy-history.component.html',
  styleUrls: ['./buy-history.component.css']
})
export class BuyHistoryComponent implements OnInit {

  bookList:any;
  date:any=[];
  constructor(public hasLogin:AuthenticationService,
    public javaService:JavaServiceService) { }


  ngOnInit() {
    if(this.hasLogin.isUserLoggedIn()){
      // this.javaService.sellUserId=sessionStorage.getItem('username');
      this.javaService.getBuyHistory().subscribe(data=>{
        console.log(data);
        this.bookList=data;
      });
      // this.javaService.buyDate(this.javaService.sellUserId).subscribe(data=>{
      //   console.log(data);
      //   this.date=data;
      // });
    }
  }

}
