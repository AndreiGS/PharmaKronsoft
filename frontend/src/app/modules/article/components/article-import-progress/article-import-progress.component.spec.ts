import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleImportProgressComponent } from './article-import-progress.component';

describe('ArticleImportProgressComponent', () => {
  let component: ArticleImportProgressComponent;
  let fixture: ComponentFixture<ArticleImportProgressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArticleImportProgressComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArticleImportProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
