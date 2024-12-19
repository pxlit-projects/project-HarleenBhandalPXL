import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {PostComponent} from "./post/post.component";
import {NavbarComponent} from "./navbar/navbar/navbar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PostComponent, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'contentbeheer-frontend';
}
