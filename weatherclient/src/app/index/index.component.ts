import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  cities:string[];

  constructor(private router:Router) {
    this.cities = [ "Singapore", "Tokyo", "USA", "Hell"]
  }

  ngOnInit(): void {
  }

  selectCity(city:string) {
    const params = {
      fields: "metric"
    }
    this.router.navigate(["/weather", city], {queryParams: params})
  }

}
