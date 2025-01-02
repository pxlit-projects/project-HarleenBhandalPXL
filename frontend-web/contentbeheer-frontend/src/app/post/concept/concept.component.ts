import {Component, inject} from '@angular/core';
import {DatePipe} from "@angular/common";
import {Post} from "../../models/post.model";
import {PostService} from "../../services/post.service";

@Component({
  selector: 'app-concept',
  standalone: true,
  imports: [
    DatePipe
  ],
  templateUrl: './concept.component.html',
  styleUrl: './concept.component.css'
})
export class ConceptComponent {
  conceptPosts: Post[] = [];
  postService: PostService = inject(PostService);

  constructor() {}

  ngOnInit() {
    this.getConceptPosts();
  }

  getConceptPosts(): void {
    this.postService.getConceptPosts().subscribe(posts => {
      this.conceptPosts = posts;
    });
  }
}
