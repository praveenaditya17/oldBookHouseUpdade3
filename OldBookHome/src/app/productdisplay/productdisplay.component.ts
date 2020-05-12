import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { LoginServeiceService } from '../share/login-serveice.service';
import { BookSellSearchComponent } from '../book-sell-search/book-sell-search.component';
import { BookSellSearchService } from '../share/book-sell-search.service';
import { JavaServiceService } from '../java-service.service';
import { NgxSpinnerService } from "ngx-spinner";
import { AuthenticationService } from '../service/authentication.service';
import { NotificationService } from '../share/notification.service';


@Component({
  selector: 'app-productdisplay',
  templateUrl: './productdisplay.component.html',
  styleUrls: ['./productdisplay.component.css']
})
export class ProductdisplayComponent implements OnInit {
  bookList:any;
  newList:any;
  notEmptyPost = true;
  notscrolly = true;
  cartDisplay=false;
  constructor(private router:Router,private loginService:BookSellSearchService,
    private dialog:MatDialog,private javaService:JavaServiceService,
    public spinner:NgxSpinnerService, private hasLogin:AuthenticationService,
    public notificationService:NotificationService) { }

  ngOnInit() {
    this.javaService.findBooks(0,3).subscribe((books: any[]) => {
      console.log(books);
      this.bookList = books;
   });
  }
  booksell(){
    this.loginService.initializeFormGroup();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "40%";
    this.dialog.open(BookSellSearchComponent,dialogConfig);

  }

  onScroll() {
    if (this.notscrolly && this.notEmptyPost){
      // this.spinner.show();
      this.notscrolly = false;
      this.loadNextPost();
    }
    
  }
  loadNextPost() {
    this.javaService.findBooks(this.bookList.length,this.bookList.length+3).subscribe((newBookList: any[]) => {
      // this.spinner.hide();
      if (newBookList.length === 0 ) {
        this.notEmptyPost =  false;
      }
      this.bookList = this.bookList.concat(newBookList);
      this.notscrolly = true;
    });
  }

  buyBook(sellOrderRequestId:number){
    this.javaService.bookId=sellOrderRequestId;
    // this.spinner.show();
    // setTimeout(() => {
    // this.spinner.hide();
    // }, 1000);
    this.javaService.getSpinner();
    this.router.navigate(['/buybook']);
  }
  addToCart(bookId:number){
    if(this.hasLogin.isUserLoggedIn()){
      this.javaService.addSellOrderRequest(bookId);
      this.notificationService.success("Added to Cart Successfully.");
    }else{
      this.notificationService.warn("please Login first.");
      console.log("not loging....")
    }
    
  }
  purchaseBook(){
    // this.spinner.show();
    // setTimeout(() => {
    // this.spinner.hide();
    // }, 1000);
    this.javaService.getSpinner();
    this.router.navigate(['/checkout']);
  }

  findBookByAuthor(author:string){
    this.notEmptyPost=false;
    this.javaService.findBookByAuthor(author).subscribe(data=>{
    //  console.log(data)
    // this.spinner.show();
    // setTimeout(() => {
    // this.spinner.hide();
    // this.bookList=data;
    // }, 1000);
    this.javaService.getSpinner();
    });
  }

  findBookByPublisher(publisher:string){
    this.javaService.getBookByPublisher(publisher).subscribe(data=>{
      this.spinner.show();
      // setTimeout(() => {
      // this.spinner.hide();
      // this.bookList=data;
      // }, 1000);
      this.javaService.getSpinner();
    });
  }

  searchCatogory(category:string){
    this.cartDisplay=true;
    this.javaService.getBookByCategory(category).subscribe(data=>{
      // this.spinner.show();
      // setTimeout(() => {
      // this.spinner.hide();
      // this.bookList=data;
      // }, 1000);
      this.javaService.getSpinner();
    });
  }

}
