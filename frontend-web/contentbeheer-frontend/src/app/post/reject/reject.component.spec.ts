import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RejectComponent } from './reject.component';
import { ReviewService } from '../../services/post/review.service';
import { PostService } from '../../services/post/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import {Post} from "../../models/post.model";

describe('RejectComponent', () => {
  let component: RejectComponent;
  let fixture: ComponentFixture<RejectComponent>;
  let reviewServiceMock: jasmine.SpyObj<ReviewService>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let routerMock: jasmine.SpyObj<Router>;
  let activatedRouteMock: any;

  beforeEach(async () => {
    reviewServiceMock = jasmine.createSpyObj('ReviewService', ['rejectPost']);
    postServiceMock = jasmine.createSpyObj('PostService', ['getPendingPosts']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);
    activatedRouteMock = { snapshot: { params: { id: 1 } } };

    await TestBed.configureTestingModule({
      imports: [RejectComponent, BrowserAnimationsModule],
      providers: [
        { provide: ReviewService, useValue: reviewServiceMock },
        { provide: PostService, useValue: postServiceMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: activatedRouteMock },
        provideHttpClient()
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RejectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call rejectPost and navigate on valid form submission', () => {
    const form = { valid: true, value: { author: 'Test Author', reason: 'Test Reason' } };
    const mockPost: Post = { title: 'Test Title', content: 'Test Content', author: 'Test Author' };
    reviewServiceMock.rejectPost.and.returnValue(of(mockPost));
    component.onSubmit(form);
    expect(reviewServiceMock.rejectPost).toHaveBeenCalledWith(1, 'Test Reason', 'Test Author');
    expect(postServiceMock.getPendingPosts).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/pending']);
    expect(component.errorMessage).toBe('');
  });
});
