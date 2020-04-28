import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { BookDetails, JavaServiceService } from '../java-service.service';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { BookreqAddressComponent } from '../bookreq-address/bookreq-address.component';
import { AddAddressService } from '../share/add-address.service';

@Component({
  selector: 'app-booksell',
  templateUrl: './booksell.component.html',
  styleUrls: ['./booksell.component.css']
})
export class BooksellComponent implements OnInit {

  book:any;
  name:any;
  array:any;
  // bookObj=new BookDetails();
  isBook:boolean=false;
  constructor(public dialog: MatDialog,private addressService:AddAddressService,private httpClient:HttpClient,private javaService:JavaServiceService) { }

  sendData(){
     this.name=this.book.value;

     //console.log(this.name.isbnNo);

       this.httpClient.get('https://www.googleapis.com/books/v1/volumes?q='+this.name.isbnNo).subscribe(
         data=>{
           
            this.array=data;
            // console.log(this.array.items[0]);
       
            this.isBook=true;
            // this.javaService.requestBookDetails(this.bookObj);
         }
       );
     }

  ngOnInit(): void {
    this.book = new FormGroup({
      isbnNo: new FormControl('')
    });
   
    this.name=this.javaService.bookISBN;
    this.httpClient.get('https://www.googleapis.com/books/v1/volumes?q='+this.name.bookISBN).subscribe(
         data=>{
           
            this.array=data;
            // console.log(this.array.items[0]);
            this.isBook=true;
            // this.javaService.requestBookDetails(this.bookObj);
         }
    );
  }
  sellBook(bookNumber:number){
    console.log(this.array.items[bookNumber]);
      this.javaService.bookObj.book_name=this.array.items[bookNumber].volumeInfo.title;
           this.javaService.bookObj.authors= this.array.items[bookNumber].volumeInfo.authors[0];
           try {
           this.javaService.bookObj.description=this.array.items[bookNumber].volumeInfo.description.substring(0,255);
             
           } catch (error) {
           this.javaService.bookObj.description='No Description';
             
           }
          //  this.bookObj.description=this.array.items[bookNumber].volumeInfo.description.substring(0,255);
          //  console.log(this.bookObj.description.substring(0,255));
           this.javaService.bookObj.publisher=this.array.items[bookNumber].volumeInfo.publisher;
           this.javaService.bookObj.publishedDate=this.array.items[bookNumber].volumeInfo.publishedDate;
           try {
            this.javaService.bookObj.categories=this.array.items[bookNumber].volumeInfo.categories[0];
              
           } catch (error) {
            this.javaService.bookObj.categories='Miscellaneous';     
           }
           this.javaService.bookObj.contentVersion=this.array.items[bookNumber].volumeInfo.contentVersion;
           this.javaService.bookObj.isbn_type_10=this.array.items[bookNumber].volumeInfo.industryIdentifiers[0].type;
           this.javaService.bookObj.isbnNo1=this.array.items[bookNumber].volumeInfo.industryIdentifiers[0].identifier;
           this.javaService.bookObj.isbn_type_13=this.array.items[bookNumber].volumeInfo.industryIdentifiers[1].type;
           this.javaService.bookObj.isbnNo2=this.array.items[bookNumber].volumeInfo.industryIdentifiers[1].identifier;
           try{
            this.javaService.bookObj.smallThumbnail=this.array.items[bookNumber].volumeInfo.imageLinks.smallThumbnail;
            this.javaService.bookObj.thumbnail=this.array.items[bookNumber].volumeInfo.imageLinks.thumbnail;
           }catch(error){
            this.javaService.bookObj.smallThumbnail="";
            this.javaService.bookObj.thumbnail="";
           }
           this.javaService.bookObj.checkPrice=this.array.items[bookNumber].saleInfo.isEbook;
           if(this.javaService.bookObj.checkPrice){
                this.javaService.bookObj.amount =this.array.items[bookNumber].saleInfo.listPrice.amount;
                this.javaService.bookObj.currencyCode=this.array.items[bookNumber].saleInfo.listPrice.currencyCode;
           }
           
           this.addressService.initializeFormGroup();
            const dialogConfig = new MatDialogConfig();
            dialogConfig.disableClose = true;
            dialogConfig.autoFocus = true;
            dialogConfig.width = "50%";
            this.dialog.open(BookreqAddressComponent,dialogConfig);
           
  }
}
