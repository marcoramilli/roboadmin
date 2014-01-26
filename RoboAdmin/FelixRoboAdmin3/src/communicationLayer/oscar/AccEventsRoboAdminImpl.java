package communicationLayer.oscar;

import com.aol.acc.*;
import communicationLayer.service.IControllerService;
import db.service.IDataBaseService;

/**
 * Riceve eventi dalla connessione OSCAR.
 * 
 * @author Luca
 */
class AccEventsRoboAdminImpl implements AccEvents
{
	private OSCAR service;
	private IDataBaseService dataBase;
	private IControllerService controller;

	public AccEventsRoboAdminImpl(OSCAR service, IDataBaseService dataBase, IControllerService controller)
	{
		this.service = service;
		this.dataBase = dataBase;
		this.controller = controller;
	}

	public void OnStateChange(AccSession session, AccSessionState state, AccResult hr)
	{
		System.out.println("OSCAR: stato connessione " + state + " (risultato: " + hr + ")");
		if (state == AccSessionState.Online)
		{
			System.out.println("OSCAR: login riuscito!");
			service.updateBuddyList();
			service.printBuddyList();
		}
	}

	public void OnNewSecondarySession(AccSession session, AccSecondarySession secondarySession, int serviceId)
	{
		try
		{
			System.out.println("OSCAR: " + secondarySession.getRemoteUserName() + " ha avviato una conversazione.");
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: avviata nuova sessione secondaria.");
		}
	}

	public void OnSecondarySessionStateChange(AccSession session, AccSecondarySession secondarySession, AccSecondarySessionState state, AccResult hr)
	{
		try
		{
			if (state == AccSecondarySessionState.ReceivedProposal)
			{
				if (!(secondarySession instanceof AccImSession))
				{
					secondarySession.reject(null);
					System.out.println("OSCAR: sessione di tipo " + secondarySession.getClass().getSimpleName() + " non supportata e rifiutata.");
				}
				else
				{
					secondarySession.accept();
				}
			}
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: eccezione in sessione secondaria " + e.errorCode);
		}
	}

	public void OnParticipantJoined(AccSession session, AccSecondarySession secondarySession, AccParticipant participant)
	{
		try
		{
			if (!dataBase.verificaNuovoUtente(participant.getName(), service.getClass().getSimpleName()))
			{
				secondarySession.endSession();
				service.removeBuddy(participant.getUser());
				System.out.println("OSCAR: utente non autorizzato rimosso dalla conversazione.");
			}
			else
			{
				service.addBuddy(participant.getUser());
			}

			//chat di gruppo non consentita
			if (secondarySession.getParticipants().length > 2)
			{
				secondarySession.endSession();
				System.out.println("OSCAR: conversazione di gruppo bloccata.");
			}
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: eccezione in sessione secondaria " + e.errorCode);
		}
	}

	public void OnImReceived(AccSession session, AccImSession imSession, AccParticipant participant, AccIm im)
	{
		try
		{
			String name = participant.getName();
			String msg = im.getConvertedText("text/plain");
			System.out.println("OSCAR: " + name + " scrive -> " + msg);
			controller.onPrivateMessage(service, imSession, msg);
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: errore di ricezione (" + e.errorCode + ")");
			e.printStackTrace();
		}
	}

	public void OnBuddyAdded(AccSession session, AccGroup group, AccUser user, int position, AccResult hr)
	{
		try
		{
			System.out.println("OSCAR: buddy " + user.getName() + " aggiunto al gruppo " + group.getName() + " (risultato " + hr + ").");
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: buddy aggiunto (risultato " + hr + ").");
		}
	}

	public void OnBuddyRemoved(AccSession session, AccGroup group, AccUser user, AccResult hr)
	{
		try
		{
			System.out.println("OSCAR: buddy " + user.getName() + " rimosso dal gruppo " + group.getName() + " (risultato " + hr + ").");
		}
		catch (AccException e)
		{
			System.out.println("OSCAR: buddy rimosso (risultato " + hr + ").");
		}
	}

	//eventi non interessanti
	public void OnSessionChange(AccSession session, AccSessionProp property)
	{
	}

	public void OnIdleStateChange(AccSession arg0, int arg1)
	{
	}

	public void OnInstanceChange(AccSession arg0, AccInstance arg1, AccInstance arg2, AccInstanceProp arg3)
	{
	}

	public void OnLookupUsersResult(AccSession arg0, String[] arg1, int arg2, AccResult arg3, AccUser[] arg4)
	{
	}

	public void OnSearchDirectoryResult(AccSession arg0, int arg1, AccResult arg2, AccDirEntry arg3)
	{
	}

	public void OnSendInviteMailResult(AccSession arg0, int arg1, AccResult arg2)
	{
	}

	public void OnRequestServiceResult(AccSession arg0, int arg1, AccResult arg2, String arg3, int arg4, AccVariant arg5)
	{
	}

	public void OnConfirmAccountResult(AccSession arg0, int arg1, AccResult arg2)
	{
	}

	public void OnReportUserResult(AccSession arg0, AccUser arg1, int arg2, AccResult arg3, int arg4, int arg5)
	{
	}

	public void OnAlertReceived(AccSession session, AccAlert alert)
	{
	}

	public void OnPushBuddyFeedResult(AccSession arg0, int arg1, AccResult arg2, String arg3)
	{
	}

	public void OnPreferenceResult(AccSession arg0, String arg1, int arg2, String arg3, AccResult arg4)
	{
	}

	public void OnPreferenceChange(AccSession arg0, String arg1, AccResult arg2)
	{
	}

	public void OnPreferenceInvalid(AccSession arg0, String arg1, AccResult arg2)
	{
	}

	public void OnPluginChange(AccSession arg0, AccPluginInfo arg1, AccPluginInfoProp arg2)
	{
	}

	public void OnPluginUninstall(AccSession arg0, AccPluginInfo arg1)
	{
	}

	public void OnBartItemRequestPropertyResult(AccSession arg0, AccBartItem arg1, AccBartItemProp arg2, int arg3, AccResult arg4, AccVariant arg5)
	{
	}

	public void OnUserRequestPropertyResult(AccSession arg0, AccUser arg1, AccUserProp arg2, int arg3, AccResult arg4, AccVariant arg5)
	{
	}

	public void OnGroupAdded(AccSession arg0, AccGroup arg1, int arg2, AccResult arg3)
	{
	}

	public void OnGroupRemoved(AccSession arg0, AccGroup arg1, AccResult arg2)
	{
	}

	public void OnGroupMoved(AccSession arg0, AccGroup arg1, int arg2, int arg3, AccResult arg4)
	{
	}

	public void OnBuddyMoved(AccSession arg0, AccUser arg1, AccGroup arg2, int arg3, AccGroup arg4, int arg5, AccResult arg6)
	{
	}

	public void OnBuddyListChange(AccSession session, AccBuddyList buddyList, AccBuddyListProp buddyListProp)
	{
	}

	public void OnGroupChange(AccSession arg0, AccGroup arg1, AccGroupProp arg2)
	{
	}

	public void OnUserChange(AccSession session, AccUser oldUser, AccUser newUser, AccUserProp userProp, AccResult hr)
	{
	}

	public void OnChangesBegin(AccSession arg0)
	{
	}

	public void OnChangesEnd(AccSession arg0)
	{
	}

	public void OnSecondarySessionChange(AccSession session, AccSecondarySession secondarySession, int property)
	{
	}

	public void OnParticipantChange(AccSession arg0, AccSecondarySession arg1, AccParticipant arg2, AccParticipant arg3, AccParticipantProp arg4)
	{
	}

	public void OnParticipantLeft(AccSession arg0, AccSecondarySession arg1, AccParticipant arg2, AccResult arg3, String arg4, String arg5)
	{
	}

	public void OnInviteResult(AccSession arg0, AccSecondarySession arg1, String arg2, int arg3, AccResult arg4)
	{
	}

	public void OnEjectResult(AccSession arg0, AccSecondarySession arg1, String arg2, int arg3, AccResult arg4)
	{
	}

	public void BeforeImSend(AccSession arg0, AccImSession arg1, AccParticipant arg2, AccIm arg3)
	{
	}

	public void OnImSent(AccSession arg0, AccImSession arg1, AccParticipant arg2, AccIm arg3)
	{
	}

	public void OnImSendResult(AccSession arg0, AccImSession arg1, AccParticipant arg2, AccIm arg3, AccResult arg4)
	{
	}

	public void BeforeImReceived(AccSession arg0, AccImSession arg1, AccParticipant arg2, AccIm arg3)
	{
	}

	public void OnLocalImReceived(AccSession arg0, AccImSession arg1, AccIm arg2)
	{
	}

	public void OnInputStateChange(AccSession arg0, AccImSession arg1, String arg2, AccImInputState arg3)
	{
	}

	public void OnEmbedDownloadProgress(AccSession arg0, AccImSession arg1, AccIm arg2, String arg3, AccStream arg4)
	{
	}

	public void OnEmbedDownloadComplete(AccSession arg0, AccImSession arg1, AccIm arg2)
	{
	}

	public void OnEmbedUploadProgress(AccSession arg0, AccImSession arg1, AccIm arg2, String arg3, AccStream arg4)
	{
	}

	public void OnEmbedUploadComplete(AccSession arg0, AccImSession arg1, AccIm arg2)
	{
	}

	public void OnRateLimitStateChange(AccSession arg0, AccImSession arg1, AccRateState arg2)
	{
	}

	public void OnNewFileXfer(AccSession session, AccFileXferSession fileSession, AccFileXfer fileXfer)
	{
	}

	public void OnFileXferProgress(AccSession arg0, AccFileXferSession arg1, AccFileXfer arg2)
	{
	}

	public void OnFileXferCollision(AccSession arg0, AccFileXferSession arg1, AccFileXfer arg2)
	{
	}

	public void OnFileXferComplete(AccSession arg0, AccFileXferSession arg1, AccFileXfer arg2, AccResult arg3)
	{
	}

	public void OnFileXferSessionComplete(AccSession arg0, AccFileXferSession arg1, AccResult arg2)
	{
	}

	public void OnFileSharingRequestListingResult(AccSession arg0, AccFileSharingSession arg1, AccFileSharingItem arg2, int arg3, AccResult arg4)
	{
	}

	public void OnFileSharingRequestXferResult(AccSession arg0, AccFileSharingSession arg1, AccFileXferSession arg2, int arg3, AccFileXfer arg4)
	{
	}

	public void OnAvStreamStateChange(AccSession arg0, AccAvSession arg1, String arg2, AccAvStreamType arg3, AccSecondarySessionState arg4, AccResult arg5)
	{
	}

	public void OnAvManagerChange(AccSession arg0, AccAvManager arg1, AccAvManagerProp arg2, AccResult arg3)
	{
	}

	public void OnAudioLevelChange(AccSession arg0, AccAvSession arg1, String arg2, int arg3)
	{
	}

	public void OnSoundEffectReceived(AccSession arg0, AccAvSession arg1, String arg2, String arg3)
	{
	}

	public void OnCustomSendResult(AccSession arg0, AccCustomSession arg1, AccParticipant arg2, AccIm arg3, AccResult arg4)
	{
	}

	public void OnCustomDataReceived(AccSession arg0, AccCustomSession arg1, AccParticipant arg2, AccIm arg3)
	{
	}

	public void OnRequestSummariesResult(AccSession arg0, int arg1, AccResult arg2, AccVariant arg3)
	{
	}

	public void OnDeliverStoredImsResult(AccSession arg0, int arg1, AccResult arg2)
	{
	}

	public void OnDeleteStoredImsResult(AccSession arg0, int arg1, AccResult arg2)
	{
	}
}
