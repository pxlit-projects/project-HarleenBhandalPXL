import {Component, inject, OnInit} from '@angular/core';
import {PostService} from "../services/post.service";
import {Post} from "../models/post.model";
import {MatCardModule} from "@angular/material/card";
import {DatePipe} from "@angular/common";
import {data} from "autoprefixer"; // DatePipe is om de datum te formateren

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [
    MatCardModule,
    DatePipe
  ],
  templateUrl: './post.component.html',
  styleUrl: './post.component.css'
})
export class PostComponent implements OnInit{
  posts: Post[] = [];
  postService: PostService = inject(PostService);

  constructor() {}

  ngOnInit() {
    this.getPosts();
  }

  getPosts(): void {
    this.postService.getPosts().subscribe(posts => {
      this.posts = posts;
    });
  }

  protected readonly data = data;
}
