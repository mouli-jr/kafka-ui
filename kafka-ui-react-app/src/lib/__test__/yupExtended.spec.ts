import { isValidJsonObject, topicFormValidationSchema} from 'lib/yupExtended';

describe('yup extended', () => {
  describe('isValidJsonObject', () => {
    it('returns false for no value', () => {
      expect(isValidJsonObject()).toBeFalsy();
    });

    it('returns false for invalid string', () => {
      expect(isValidJsonObject('foo: bar')).toBeFalsy();
    });

    it('returns false on parsing error', () => {
      JSON.parse = jest.fn().mockImplementationOnce(() => {
        throw new Error();
      });
      expect(isValidJsonObject('{ "foo": "bar" }')).toBeFalsy();
    });

    it('returns true for valid JSON object', () => {
      expect(isValidJsonObject('{ "foo": "bar" }')).toBeTruthy();
    });
  });
});

describe('topicFormValidationSchema', () => {
  describe('minInSyncReplicas', () => {
    it('should fail if minInSyncReplicas > replicationFactor', async () => {
      const formData = {
        name: 'my-topic',
        partitions: 3,
        replicationFactor: 2,
        minInSyncReplicas: 3,
        cleanupPolicy: 'compact',
      };

      await expect(
        topicFormValidationSchema.validate(formData)
      ).rejects.toThrow(
        /Min In Sync Replicas must be less than or equal to Replication Factor/
      );
    });
    it('should pass if minInSyncReplicas <= replicationFactor', async () => {
      const formData = {
        name: 'my-topic',
        partitions: 3,
        replicationFactor: 3,
        minInSyncReplicas: 2,
        cleanupPolicy: 'delete',
      };

      await expect(topicFormValidationSchema.validate(formData)).resolves.toBe(
        formData
      );
    });
  });
});
