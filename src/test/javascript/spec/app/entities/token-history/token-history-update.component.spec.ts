import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { TokenHistoryUpdateComponent } from 'app/entities/token-history/token-history-update.component';
import { TokenHistoryService } from 'app/entities/token-history/token-history.service';
import { TokenHistory } from 'app/shared/model/token-history.model';

describe('Component Tests', () => {
  describe('TokenHistory Management Update Component', () => {
    let comp: TokenHistoryUpdateComponent;
    let fixture: ComponentFixture<TokenHistoryUpdateComponent>;
    let service: TokenHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [TokenHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TokenHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TokenHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TokenHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TokenHistory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TokenHistory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
