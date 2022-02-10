import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { WeatherdataComponent } from './weatherdata/weatherdata.component';

const appRoutes: Routes = [
  { path: "", component: IndexComponent },
  { path: "weather/:city", component: WeatherdataComponent },
  { path: "**", redirectTo: "", pathMatch: "full" }
]

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    WeatherdataComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {useHash: true})
  ],
  providers: [],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
