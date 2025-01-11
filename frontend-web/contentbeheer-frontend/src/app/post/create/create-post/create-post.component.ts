import {ChangeDetectionStrategy, Component, inject} from '@angular/core';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {PostService} from "../../../services/post/post.service";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.css'
})
export class CreatePostComponent {
  postService: PostService = inject(PostService);
  router: Router = inject(Router);
  title: string = '';
  content: string = '';
  author: string = '';
  errorMessage: string = '';

  constructor() {}

  onSubmit(form: any): void {
    if(form.valid) {
      this.createPost();
    }
    else {
      this.errorMessage = 'Form is invalid. Please fill out all required fields.';
    }
  }

  createPost(): void {
    this.errorMessage = '';
    const post = {
      title: this.title,
      content: this.content,
      author: this.author
    };
    this.postService.createPost(post).subscribe({
      next: (response) => {
        console.log('Post created successfully', response);
        this.router.navigate(['/posts']);
      },
      error: (error) => {
        console.error('Error creating post', error);
      }
    });
  }

  savePostAsConcept(): void {
    this.errorMessage = '';
    const post = {
      title: this.title,
      content: this.content,
      author: this.author
    }
    this.postService.savePostAsConcept(post).subscribe({
      next: (response) => {
        console.log('Post saved as concept successfully', response);
        this.router.navigate(['/concepts']);
      },
      error: (error) => {
        console.error('Error saving post as concept', error);
      }
    });
  }

  protected readonly onsubmit = onsubmit;
}
