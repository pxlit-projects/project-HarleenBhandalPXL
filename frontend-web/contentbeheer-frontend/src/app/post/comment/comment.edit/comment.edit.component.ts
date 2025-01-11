import {Component, inject} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {CommentService} from "../../../services/post/comment.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-comment.edit',
  standalone: true,
  imports: [
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './comment.edit.component.html',
  styleUrl: './comment.edit.component.css'
})
export class CommentEditComponent {
  commentService: CommentService = inject(CommentService);
  route: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);
  comment: string | null | undefined = '';
  commentId: number = -1;
  postId: number = -1;
  errorMessage: string = '';

  constructor() {
  }

  ngOnInit(): void {
    this.commentId = this.route.snapshot.params['id'];
    this.getComment();
  }

  onSubmit(form: any): void {
    if (form.valid) {
      this.editComment();
    } else {
      this.errorMessage = 'Form is invalid. Please fill out all required fields.';
    }
  }

  getComment(): void {
    if (this.commentId) {
      this.commentService.getComment(this.commentId).subscribe({
        next: (comment) => {
          this.comment = comment.content;
          this.postId = comment.postId;
        },
        error: (error) => {
          console.error('Error fetching comment', error);
        }
      });
    } else {
      console.error('No id found in the URL');
    }
  }

  editComment(): void {
    this.commentService.updateComment(this.commentId, this.comment).subscribe({
      next: (comment) => {
        this.router.navigate(['/posts', this.postId]);
      },
      error: (error) => {
        console.error('Error updating comment', error);
      }
    });
  }
}
