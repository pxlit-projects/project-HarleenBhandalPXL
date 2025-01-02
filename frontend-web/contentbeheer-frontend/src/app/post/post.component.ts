import {Component, inject, OnInit} from '@angular/core';
import {PostService} from "../services/post/post.service";
import {AuthService} from "../services/auth/auth.service";
import {Post} from "../models/post.model";
import {MatCardModule} from "@angular/material/card";
import {DatePipe} from "@angular/common"; // DatePipe is om de datum te formateren
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [
    MatCardModule,
    DatePipe,
    MatIconModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './post.component.html',
  styleUrl: './post.component.css'
})
export class PostComponent implements OnInit{
  posts: Post[] = [];
  postService: PostService = inject(PostService);
  authService: AuthService = inject(AuthService);

  constructor() {}

  ngOnInit() {
    this.getPosts();
  }

  getPosts(): void {
    this.postService.getPublishedPosts().subscribe(posts => {
      this.posts = posts;
    });
  }
}
