import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { TokenHistoryDetailComponent } from 'app/entities/token-history/token-history-detail.component';
import { TokenHistory } from 'app/shared/model/token-history.model';

describe('Component Tests', () => {
  describe('TokenHistory Management Detail Component', () => {
    let comp: TokenHistoryDetailComponent;
    let fixture: ComponentFixture<TokenHistoryDetailComponent>;
    const route = ({ data: of({ tokenHistory: new TokenHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TokenHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TokenHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TokenHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tokenHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tokenHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
