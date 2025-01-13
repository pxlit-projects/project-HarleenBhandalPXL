import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ConceptComponent } from './concept.component';
import { PostService } from '../../services/post/post.service';
import { AuthService } from '../../services/auth/auth.service';
import { Post } from '../../models/post.model';
import { of } from 'rxjs';

describe('ConceptComponent', () => {
  let component: ConceptComponent;
  let fixture: ComponentFixture<ConceptComponent>;
  let postServiceMock: jasmine.SpyObj<PostService>;
  let authServiceMock: jasmine.SpyObj<AuthService>;

  const mockPosts: Post[] = [
    new Post(1, 'Concept Post 1', 'Content 1', 'Author 1', '2024-01-01'),
    new Post(2, 'Concept Post 2', 'Content 2', 'Author 2', '2024-01-02'),
  ];

  beforeEach(async () => {
    postServiceMock = jasmine.createSpyObj('PostService', ['getConceptPosts', 'updateToPending']);
    authServiceMock = jasmine.createSpyObj('AuthService', ['isAuthenticated', 'getRole']);
    postServiceMock.getConceptPosts.and.returnValue(of(mockPosts));

    await TestBed.configureTestingModule({
      imports: [ConceptComponent],
      providers: [
        { provide: PostService, useValue: postServiceMock },
        { provide: AuthService, useValue: authServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ConceptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call getConceptPosts on initialization', () => {
    spyOn(component, 'getConceptPosts').and.callThrough();
    component.ngOnInit();
    expect(component.getConceptPosts).toHaveBeenCalled();
  });

  it('should set conceptPosts correctly in getConceptPosts', () => {
    component.getConceptPosts();
    expect(component.conceptPosts).toEqual(mockPosts);
  });

  it('should call updateToPending and refresh conceptPosts in updateToPending', () => {
    const mockPost = new Post(1, 'Updated Post', 'Updated Content', 'Updated Author', '2024-01-01');
    postServiceMock.updateToPending.and.returnValue(of(mockPost));
    spyOn(component, 'getConceptPosts');
    const postId = 1;
    component.updateToPending(postId);
    expect(postServiceMock.updateToPending).toHaveBeenCalledWith(postId);
    expect(component.getConceptPosts).toHaveBeenCalled();
  });
});
