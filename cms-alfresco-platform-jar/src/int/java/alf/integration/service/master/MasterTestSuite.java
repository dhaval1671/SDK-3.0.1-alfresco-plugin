package alf.integration.service.master;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import alf.cabinet.legalproceeding.brief.BriefTestSuite;
import alf.cabinet.legalproceeding.decision.DecisionTestSuite;
import alf.cabinet.legalproceeding.exhibit.ExhibitTestSuite;
import alf.cabinet.legalproceeding.motion.MotionTestSuite;
import alf.cabinet.legalproceeding.order.OrderTestSuite;
import alf.cabinet.legalproceeding.pleading.PleadingTestSuite;
import alf.cabinet.legalproceeding.proceedingnumber.ProceedingNumberTestSuite;
import alf.cabinet.legalproceeding.undesignated.UndesignatedTestSuite;
import alf.cabinet.madrid.MadridTestSuite;
import alf.cabinet.publication.eog.EogTestSuite;
import alf.cabinet.publication.idm.IdmTestSuite;
import alf.cabinet.tmcase.accessLevel.content.AccessLevelContentTestSuite;
import alf.cabinet.tmcase.accessLevel.metadata.AccessLevelMetadataTestSuite;
import alf.cabinet.tmcase.application.ApplicationTestSuite;
import alf.cabinet.tmcase.attachment.AttachmentTestSuite;
import alf.cabinet.tmcase.cases.CaseDocumentsTestSuite;
import alf.cabinet.tmcase.evidence.EvidenceTestSuite;
import alf.cabinet.tmcase.generic.mark.GenericMarkTestSuite;
import alf.cabinet.tmcase.legacy.LegacyTestSuite;
import alf.cabinet.tmcase.mark.image.MarkTestSuite;
import alf.cabinet.tmcase.mark.multimedia.audio.AudioMarkTestSuite;
import alf.cabinet.tmcase.mark.multimedia.video.VideoMarkTestSuite;
import alf.cabinet.tmcase.mark.multimedia.video.transform.TransformVideoTestSuite;
import alf.cabinet.tmcase.note.NoteTestSuite;
import alf.cabinet.tmcase.notice.NoticeTestSuite;
import alf.cabinet.tmcase.officeaction.OfficeActionTestSuite;
import alf.cabinet.tmcase.receipt.ReceiptTestSuite;
import alf.cabinet.tmcase.redaction.evidence.RedactionEvidenceTestSuite;
import alf.cabinet.tmcase.redaction.response.RedactionResponseTestSuite;
import alf.cabinet.tmcase.redaction.withdrawal.RedactionWithdrawalTestSuite;
import alf.cabinet.tmcase.registrationcertificate.RegistrationCertificateTestSuite;
import alf.cabinet.tmcase.response.ResponseTestSuite;
import alf.cabinet.tmcase.signature.SignatureTestSuite;
import alf.cabinet.tmcase.summary.SummaryTestSuite;
import alf.cabinet.tmcase.teaspdf.TeasPdfTestSuite;
import alf.cabinet.tmcase.updatedregistrationcertificate.UpdatedRegCertTestSuite;
import alf.cabinet.tmcase.withdrawal.WithdrawalTestSuite;
import alf.cabinet.xmlxsldtd.ticrs.LegacyTicrsTestSuite;
import alf.documentlibrary.evidencebank.twoa.DocumentLibraryTestSuite;
import alf.documentlibrary.webcapture.WebcaptureTestSuite;
import alf.drive.efile.EfileTestSuite;
import alf.drive.eogtemplate.EogTemplateTestSuite;
import alf.integration.service.checks.PreliminaryHealthCheckTestSuite;
import alf.integration.service.cleanup.CleanupTestSuite;
import alf.integration.service.generic.retrieve.versions.RetrieveVersionsTestSuite;
import alf.integration.service.retrieveContentAsAttachment.RetrieveContentAsAttachmentTestSuite;
import alf.integration.service.ticrs.TicrsTestSuite;
import alf.intergration.healthstatus.AlfrescoHealthStatusTestSuite;
import alf.tmcontent.ais.ticrsdocument.TicrsDocumentTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * MasterTestSuite.
 * 
 * @author Sanjay Tank {linkedin.com/in/sanjaytaunk}
 *
 */


@RunWith(Suite.class)
@SuiteClasses({ CleanupTestSuite.class, PreliminaryHealthCheckTestSuite.class, AlfrescoHealthStatusTestSuite.class, 
    
    /*cabinet -> case*/
    AttachmentTestSuite.class,
        VideoMarkTestSuite.class, AudioMarkTestSuite.class, MarkTestSuite.class, EvidenceTestSuite.class, DocumentLibraryTestSuite.class, OfficeActionTestSuite.class,
        NoteTestSuite.class, LegacyTestSuite.class, NoticeTestSuite.class, SummaryTestSuite.class, ReceiptTestSuite.class,
        ResponseTestSuite.class, SignatureTestSuite.class, CaseDocumentsTestSuite.class, RedactionResponseTestSuite.class,
        RedactionEvidenceTestSuite.class, RedactionWithdrawalTestSuite.class, EogTemplateTestSuite.class,
        GenericMarkTestSuite.class, TicrsTestSuite.class, WithdrawalTestSuite.class,
        TicrsDocumentTestSuite.class, AccessLevelContentTestSuite.class, AccessLevelMetadataTestSuite.class,
        ApplicationTestSuite.class, RetrieveVersionsTestSuite.class, RegistrationCertificateTestSuite.class,
        UpdatedRegCertTestSuite.class, TeasPdfTestSuite.class, TransformVideoTestSuite.class,
        RetrieveContentAsAttachmentTestSuite.class, 
        /*cabinet -> publication -> [idmanual, official-gazatte]*/
        EogTestSuite.class,IdmTestSuite.class,
        /*cabinet -> madridib*/
        MadridTestSuite.class,
        /*cabinet -> legal-proceeding*/
        PleadingTestSuite.class, MotionTestSuite.class,ExhibitTestSuite.class,BriefTestSuite.class,
        OrderTestSuite.class,DecisionTestSuite.class,UndesignatedTestSuite.class,
        ProceedingNumberTestSuite.class,
        /*drive -> efile*/
        EfileTestSuite.class,
        WebcaptureTestSuite.class,
        
        /*Legacy Ticrs ie Cabinet -> xml-xsl-dtd*/
        LegacyTicrsTestSuite.class
        
        
})
public class MasterTestSuite {

    /**
     * Suite.
     *
     * @return the test
     */
    public Test suite() {
        return new TestSuite(MasterTestSuite.class.getName());
    }

}