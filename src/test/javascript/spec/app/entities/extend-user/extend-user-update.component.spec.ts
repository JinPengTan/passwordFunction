import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterTestModule } from '../../../test.module';
import { ExtendUserUpdateComponent } from 'app/entities/extend-user/extend-user-update.component';
import { ExtendUserService } from 'app/entities/extend-user/extend-user.service';
import { ExtendUser } from 'app/shared/model/extend-user.model';

describe('Component Tests', () => {
  describe('ExtendUser Management Update Component', () => {
    let comp: ExtendUserUpdateComponent;
    let fixture: ComponentFixture<ExtendUserUpdateComponent>;
    let service: ExtendUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestModule],
        declarations: [ExtendUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExtendUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExtendUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExtendUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExtendUser(123);
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
        const entity = new ExtendUser();
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
