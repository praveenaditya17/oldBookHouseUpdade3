import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { JavaServiceService } from '../java-service.service';

@Component({
  selector: 'app-sell-history',
  templateUrl: './sell-history.component.html',
  styleUrls: ['./sell-history.component.css']
})
export class SellHistoryComponent implements OnInit {

  bookList:any;
  constructor(public hasLogin:AuthenticationService,
    public javaService:JavaServiceService) { }
    
  ngOnInit() {
    if(this.hasLogin.isUserLoggedIn()){
      this.javaService.getSellHistory().subscribe(data=>{
        console.log(data);
        this.bookList=data;
      });
    }
  }
}
