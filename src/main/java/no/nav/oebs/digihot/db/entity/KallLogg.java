package no.nav.oebs.digihot.db.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "XXRTV_DIGIHOT_LOGG")
public class KallLogg {

	public static final String RETNING_INN = "INN";
	public static final String RETNING_UT = "UT";

	public static final String TYPE_PLSQL = "PLSQL";
	public static final String TYPE_REST = "REST";
	public static final String METHOD_POST = "POST";
	@Id
	@SequenceGenerator(name = "XXRTV_DIGIHOT_API_SEQ", sequenceName = "XXRTV_DIGIHOT_API_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXRTV_DIGIHOT_API_SEQ")
	@Column(name = "KALL_LOGG_ID")
	private Long id;

	@Column(name = "KORRELASJON_ID")
	private String korrelasjonId;

	@Column(name = "TIDSPUNKT")
	private LocalDateTime tidspunkt;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "KALL_RETNING")
	private String kallRetning;

	@Column(name = "METHOD")
	private String method;

	@Column(name = "OPERATION")
	private String operation;

	@Column(name = "STATUS")
	private Integer status;

	// Brukes for beregning av kalltid.
	@Transient
	private long startTid;

	@Column(name = "KALLTID")
	private Long kalltid;

	@Column(name = "REQUEST")
	private String request;

	@Column(name = "RESPONSE")
	private String response;

	@Column(name = "LOGGINFO")
	private String logginfo;
}
