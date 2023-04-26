package org.eclipse.tractusx.ssi.lib.model.verifiable.credential;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.*;
import org.eclipse.tractusx.ssi.lib.model.Proof;

// @formatter:off
/**
 * Verifiable Credential e.g. from
 * https://www.w3.org/TR/vc-data-model/#example-usage-of-the-credentialsubject-property {
 * "@context": [ "https://www.w3.org/2018/credentials/v1",
 * "https://www.w3.org/2018/credentials/examples/v1", "https://w3id.org/secu
 * rity/suites/ed25519-2020/v1" ], "id": "http://example.edu/credentials/3732", "type": [
 * "VerifiableCredential", "UniversityDegreeCredential" ], "issuer":
 * "https://example.edu/issuers/565049", "issuanceDate": "2010-01-01T00:00:00Z",
 * "credentialSubject": { "id": "did:example:ebfeb1f712ebc6f1c276e12ec21", "degree": { "type":
 * "BachelorDegree", "name": "Bachelor of Science and Arts" } }, "proof": { "type":
 * "Ed25519Signature2020", "created": "2022-02-25T14:58:43Z", "verificationMethod":
 * "https://example.edu/issuers/565049#key-1", "proofPurpose": "assertionMethod", "proofValue":
 * "zeEdUoM7m9cY8ZyTpey83yBKeBcmcvbyrEQzJ19rD2UXArU2U1jPGoEt rRvGYppdiK37GU4NBeoPakxpWhAvsVSt" } }
 */
// @formatter:on
@ToString
public class VerifiableCredential extends HashMap<String, Object> {

  public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  public static final String CONTEXT = "@context";
  public static final String ID = "id";
  public static final String TYPE = "type";
  public static final String ISSUER = "issuer";
  public static final String ISSUANCE_DATE = "issuanceDate";
  public static final String EXPIRATION_DATE = "expirationDate";
  public static final String CREDENTIAL_SUBJECT = "credentialSubject";
  public static final String PROOF = "proof";

  public VerifiableCredential(Map<String, Object> json) {
    super(json);

    try {
      // validate getters
      Objects.requireNonNull(this.getContext());
      Objects.requireNonNull(this.getId());
      Objects.requireNonNull(this.getTypes());
      Objects.requireNonNull(this.getIssuer());
      Objects.requireNonNull(this.getIssuanceDate());
      Objects.requireNonNull(this.getCredentialSubject());
      this.getExpirationDate();
      this.getProof();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid VerifiableCredential", e);
    }
  }

  public List<String> getContext() {
    return (List<String>) this.get(CONTEXT);
  }

  @NonNull
  public URI getId() {
    return URI.create((String) this.get(ID));
  }

  @NonNull
  public List<String> getTypes() {
    return (List<String>) this.get(TYPE);
  }

  @NonNull
  public URI getIssuer() {
    return URI.create((String) this.get(ISSUER));
  }

  @NonNull
  public Instant getIssuanceDate() {
    return Instant.parse((String) this.get(ISSUANCE_DATE));
  }

  public Instant getExpirationDate() {
    return Instant.parse((String) this.get(EXPIRATION_DATE));
  }

  @NonNull
  public VerifiableCredentialSubject getCredentialSubject() {
    final Object subject = this.get(CREDENTIAL_SUBJECT);
    if (subject == null) {
      return null;
    }

    return new VerifiableCredentialSubject((Map<String, Object>) subject);
  }

  public Proof getProof() {
    final Object subject = this.get(PROOF);
    if (subject == null) {
      return null;
    }

    return new Proof((Map<String, Object>) subject);
  }
}
