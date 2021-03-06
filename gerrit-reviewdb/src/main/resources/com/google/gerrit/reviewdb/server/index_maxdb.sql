delimiter #
-- Gerrit 2 : MaxDB
--

-- Indexes to support @Query
--

-- *********************************************************************
-- AccountAccess
--    covers:             byPreferredEmail
CREATE INDEX accounts_byPreferredEmail
ON accounts (preferred_email)
#


-- *********************************************************************
-- AccountGroupMemberAccess
--    @PrimaryKey covers: byAccount
CREATE INDEX account_group_members_byGroup
ON account_group_members (group_id)
#

-- *********************************************************************
-- AccountGroupIncludeByUuidAccess
--    @PrimaryKey covers: byGroup
CREATE INDEX acc_gr_incl_by_uuid_byInclude
ON account_group_by_id (include_uuid)
#


-- *********************************************************************
-- ApprovalCategoryAccess
--    too small to bother indexing


-- *********************************************************************
-- ApprovalCategoryValueAccess
--     @PrimaryKey covers: byCategory


-- *********************************************************************
-- BranchAccess
--    @PrimaryKey covers: byProject


-- *********************************************************************
-- ChangeMessageAccess
--    @PrimaryKey covers: byChange

--    covers:             byPatchSet
CREATE INDEX change_messages_byPatchset
ON change_messages (patchset_change_id, patchset_patch_set_id)
#

-- *********************************************************************
-- PatchLineCommentAccess
--    @PrimaryKey covers: published, draft
CREATE INDEX patch_comment_drafts
ON patch_comments (status, author_id)
#

-- *********************************************************************
-- PatchSetAccess
CREATE INDEX patch_sets_byRevision
ON patch_sets (revision)
#
