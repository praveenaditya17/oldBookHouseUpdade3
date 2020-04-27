import { Component, OnInit } from '@angular/core';
import { JavaServiceService } from '../java-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-book',
  templateUrl: './show-book.component.html',
  styleUrls: ['./show-book.component.css']
})
export class ShowBookComponent implements OnInit {
  bookList:any;
  constructor(public javaService:JavaServiceService,public router:Router) { 
    this.bookList=this.javaService.bookList;
  }

  ngOnInit() {
  }
  viewBook(sellOrderRequestId:number){
    this.javaService.bookId=sellOrderRequestId;
    this.router.navigate(['/buybook']);
  }
  addToCart(bookId:number){
    this.javaService.addSellOrderRequest(bookId);
  }

}
