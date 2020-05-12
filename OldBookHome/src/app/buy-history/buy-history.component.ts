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
  constructor(public hasLogin:AuthenticationService,
    public javaService:JavaServiceService) { }

  ngOnInit() {
    if(this.hasLogin.isUserLoggedIn()){
      this.javaService.getBuyHistory().subscribe(data=>{
        console.log(data);
        this.bookList=data;
      });
    }
  }

}
