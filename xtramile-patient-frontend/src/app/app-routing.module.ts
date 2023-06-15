import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientComponent } from './components/home/patient/patient.component';
import { DashboardComponent } from './components/home/dashboard/dashboard.component';

const routes: Routes = [
  {
    path: '',
    // redirectTo: '/patient',
    component: HomeComponent,
    // pathMatch: 'full',
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'patient', component: PatientComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
