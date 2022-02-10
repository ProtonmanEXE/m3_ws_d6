import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CityDetailsService } from '../shared/citydetails.service';
import { Weather } from '../shared/model';

@Component({
  selector: 'app-weatherdata',
  templateUrl: './weatherdata.component.html',
  styleUrls: ['./weatherdata.component.css']
})
export class WeatherdataComponent implements OnInit {

  weather!: Weather

  city:string;
  fields:string;

  constructor(private activatedRoute:ActivatedRoute,
              private citySvc:CityDetailsService) {
    this.city = "";
    this.fields = ""
  }

  ngOnInit(): void {
    this.city = this.activatedRoute.snapshot.params["city"];
    this.fields = this.activatedRoute.snapshot.queryParams['fields'];

    this.citySvc.getWeather(this.city)
      .then(w => this.weather = w)
  }

}
