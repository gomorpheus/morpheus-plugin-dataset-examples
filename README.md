# Morpheus Plugin Dataset Examples

A Morpheus plugin containing example dataset providers.

### What is a Dataset Provider?

A `DatasetProvider` loads dynamic data for custom form inputs as well as data references with scribe templates.

### Getting Started

1) Create a dataset provider by implementing the `DataSetProvider` class or with some convenience methods an `AbstractDataSetProvider` class.
1) Register the dataset provider in the plugin class.
    ```groovy
    @Override
    void initialize() {
        ...
        this.registerProvider(new FooDatasetProvider(this, this.morpheus))
    }
   ```
2) Use the dataset provider in a `OptionType` or `OptionSource`. The `optionSource` property must correlate to `getKey()` in the dataset provider. Avoid naming conflicts with other plugins by using a namespaces. A namespace is set on the `namespace` property of the `DatasetInfo` in `getDataset()`. Alternatively, the `getNamespace()` method can be implemented or overridden. When a dataset is namespaced the  `optionSourceType` should correlate to the dataset's namespace.

### Additional Information
Learn more about `DatasetProviders` in the [Morpheus Plugin Documentation](https://developer.morpheusdata.com/docs#dataset-providers).
