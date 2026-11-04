using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace ShipAPI.Models;

public partial class PostgresContext : DbContext
{
    public PostgresContext()
    {
    }

    public PostgresContext(DbContextOptions<PostgresContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Cabin> Cabins { get; set; }
    public virtual DbSet<Location> Locations { get; set; }
    public virtual DbSet<Schedule> Schedules { get; set; }
    public virtual DbSet<Status> Statuses { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Cabin>(entity =>
        {
            entity.HasKey(e => e.IdCabin).HasName("cabin_pkey");
            entity.ToTable("cabin"); 
            entity.HasIndex(e => e.Number, "cabin_number_key").IsUnique();

            entity.Property(e => e.IdCabin).HasColumnName("id_cabin");
            entity.Property(e => e.Deck).HasDefaultValue(1).HasColumnName("deck");
            entity.Property(e => e.IdStatus).HasColumnName("id_status");
            
            entity.Property(e => e.Number).HasColumnName("number");

            entity.HasOne(d => d.IdStatusNavigation).WithMany(p => p.Cabins)
                .HasForeignKey(d => d.IdStatus)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("cabin_id_status_fkey");
        });

        modelBuilder.Entity<Location>(entity =>
        {
            entity.HasKey(e => e.IdLocation).HasName("location_pk");
            entity.ToTable("location"); 

            entity.Property(e => e.IdLocation).HasColumnName("id_location");
            entity.Property(e => e.Position).HasColumnName("position");
            entity.Property(e => e.Title).HasColumnName("title");
        });

        modelBuilder.Entity<Schedule>(entity =>
        {
            entity.HasKey(e => e.IdSchedule).HasName("schedule_pk");
            entity.ToTable("schedule"); 

            entity.Property(e => e.IdSchedule).HasColumnName("id_schedule");
            entity.Property(e => e.Date).HasColumnName("date");
            entity.Property(e => e.EndTime).HasColumnName("end_time");
            entity.Property(e => e.IdLocation).HasColumnName("id_location");
            entity.Property(e => e.StartTime).HasColumnName("start_time");
            entity.Property(e => e.Title).HasColumnName("title");

            entity.HasOne(d => d.IdLocationNavigation).WithMany(p => p.Schedules)
                .HasForeignKey(d => d.IdLocation)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("schedule_location_fk");
        });

        modelBuilder.Entity<Status>(entity =>
        {
            entity.HasKey(e => e.IdStatus).HasName("status_pkey");
            entity.ToTable("status");

            entity.Property(e => e.IdStatus).HasColumnName("id_status");
            entity.Property(e => e.Description).HasColumnName("description");
            entity.Property(e => e.Name).HasMaxLength(255).HasColumnName("name");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}