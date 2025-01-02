import {Component, inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PostService} from "../../services/post/post.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-edit',
  standalone: true,
  imports: [
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css'
})
export class EditComponent {
  postService: PostService = inject(PostService);
  router: Router = inject(Router);
  route: ActivatedRoute = inject(ActivatedRoute);
  title: string = '';
  content: string = '';
  author: string = '';
  postId: number = -1;

  constructor() {}

  ngOnInit(): void {
    this.postId = this.route.snapshot.params['id'];

    if (this.postId) {
      this.postService.getPost(this.postId).subscribe({
        next: (post) => {
          this.title = post.title;
          this.content = post.content;
          this.author = post.author;
        },
        error: (error) => {
          console.error('Error fetching post', error);
        }
      });
    } else {
      console.error('No id found in the URL');
    }

    this.postService.getPost(this.postId).subscribe({
      next: (post) => {
        this.title = post.title;
        this.content = post.content;
        this.author = post.author;
      },
      error: (error) => {
        console.error('Error fetching post', error);
      }
    });
  }

  editPost(): void {
    const editedPost = {
      title: this.title,
      content: this.content,
      author: this.author
    };
    this.postService.editPost(this.postId, editedPost).subscribe({
      next: (response) => {
        console.log('Post updated successfully', response);
        this.router.navigate(['/posts']);
      },
      error: (error) => {
        console.error('Error updating post', error);
      }
    });
  }
}
