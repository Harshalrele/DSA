package boberbackend.service;

import boberbackend.controllers.common.BadRequestException;
import boberbackend.controllers.common.VisitSearchReq;
import boberbackend.controllers.doctor.VisitCompleteReq;
import boberbackend.controllers.doctor.VisitDetailsReq;
import boberbackend.controllers.doctor.SetVisitStatusReq;
import boberbackend.controllers.receptionist.VisitCreateReq;
import boberbackend.jpa.model.Visit;

import java.util.List;

public interface VisitService {

    Visit createNewVisit(VisitCreateReq req) throws BadRequestException;

    List<Visit> searchVisits(VisitSearchReq req);

    Visit getVisitById(Long visitId);

    Visit completeVisit(VisitCompleteReq req);

    Visit cancelVisit(Long id) throws BadRequestException;

    Visit setVisitStatus(SetVisitStatusReq req) throws BadRequestException;

    Visit setVisitDetails(VisitDetailsReq req) throws BadRequestException;
}

