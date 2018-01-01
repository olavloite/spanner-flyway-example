package nl.topicus.spanner.flyway.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

@MappedSuperclass
public class BaseEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final NoArgGenerator GENERATOR = Generators.randomBasedGenerator();

	@Id
	private Long id;

	@Column(nullable = false, columnDefinition = "BYTES(16)", unique = true)
	private UUID uuid;

	@Column(nullable = false)
	private Date created;

	@Column(nullable = false)
	private Date updated;

	/**
	 * Call this method to manually initialize the fields that are normally
	 * automatically set before persistment.
	 */
	public void initId()
	{
		onCreate();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public UUID getUuid()
	{
		if (uuid == null)
			uuid = GENERATOR.generate();
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	@PrePersist
	protected void onCreate()
	{
		created = new Date();
		updated = new Date();
		if (uuid == null)
			uuid = GENERATOR.generate();
		if (id == null)
			id = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
	}

	@PreUpdate
	protected void onUpdate()
	{
		updated = new Date();
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (!(other instanceof BaseEntity))
			return false;

		return ((BaseEntity) other).getUuid().equals(this.getUuid());
	}

	@Override
	public int hashCode()
	{
		return getUuid().hashCode();
	}

	public boolean isSaved()
	{
		return getId() != null;
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

	public Date getUpdated()
	{
		return updated;
	}

	public void setUpdated(Date updated)
	{
		this.updated = updated;
	}

}
