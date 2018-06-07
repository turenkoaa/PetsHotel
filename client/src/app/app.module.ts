import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {HttpClientModule} from '@angular/common/http';


import {AppComponent} from './app.component';
import {MaterialModule} from './core/materilal.module';
import {PetsItemComponent} from './pets/pets-item/pets-item.component';
import {PetsFeedComponent} from './pets/pets-feed/pets-feed.component';
import {metaReducers, reducers} from './ngrx';
import {PetsEffects} from './pets/ngrx/pets.effects';
import {PetsService} from './services/pet.service';
import {UserCabinetComponent} from './user-cabinet/user-cabinet.component';
import {UserInfoComponent} from './user-cabinet/user-info/user-info.component';
import {CabinetService} from './services/cabinet.service';
import {CabinetEffects} from './user-cabinet/ngrx/cabinet.effects';
import {PetsInfoComponent} from './user-cabinet/pets-info/pets-info.component';
import {RequestsInfoComponent} from './user-cabinet/requests-info/requests-info.component';
import {ResponcesInfoComponent} from './user-cabinet/responces-info/responces-info.component';
import {RequestComponent} from './user-cabinet/request/request.component';
import {ResponseComponent} from './user-cabinet/response/response.component';
import {RequestsNewComponent} from './user-cabinet/requests-new/requests-new.component';
import {ResponsesForRequestComponent} from './user-cabinet/responses-for-request/responses-for-request.component';
import {AdminComponent} from './user-cabinet/admin/admin.component';
import {UnauthorisedUserComponent} from './user-cabinet/unauthorised-user/unauthorised-user.component';
import {UserComponent} from './user-cabinet/user/user.component';
import {ReactiveFormsModule} from '@angular/forms';
import {PetComponent} from './user-cabinet/pet/pet.component';
import {AppRoutingModule} from './/app-routing.module';
import {LoginComponent} from './login/login.component';


@NgModule({
  declarations: [
    AppComponent,
    PetsFeedComponent,
    PetsItemComponent,
    UserCabinetComponent,
    UserInfoComponent,
    PetsInfoComponent,
    RequestsInfoComponent,
    ResponcesInfoComponent,
    RequestComponent,
    ResponseComponent,
    RequestsNewComponent,
    ResponsesForRequestComponent,
    AdminComponent,
    UnauthorisedUserComponent,
    UserComponent,
    PetComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    NoopAnimationsModule,
    MaterialModule,
    HttpClientModule,
    StoreModule.forRoot(reducers, { metaReducers: metaReducers }),
    EffectsModule.forRoot([PetsEffects, CabinetEffects]),
    ReactiveFormsModule,
    AppRoutingModule,
  ],
  providers: [
    PetsService,
    CabinetService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
